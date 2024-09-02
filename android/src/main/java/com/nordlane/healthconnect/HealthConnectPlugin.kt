package com.nordlane.healthconnect

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.impl.converters.datatype.RECORDS_TYPE_NAME_MAP
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.lifecycle.lifecycleScope
import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import kotlinx.coroutines.launch
import org.json.JSONObject


@SuppressLint("RestrictedApi")
@CapacitorPlugin(name = "HealthConnect")
class HealthConnectPlugin : Plugin() {

    private val implementation = HealthConnect()
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(this.context.applicationContext) }
    private val permissionContract by lazy {
        PermissionController.createRequestPermissionResultContract()
    }

    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", implementation.echo(value))
        call.resolve(ret)
    }

    @PluginMethod
    fun checkAvailabilityAndConnect(call: PluginCall) {
        val availability = when (val status = HealthConnectClient.getSdkStatus(this.context , "com.google.android.apps.healthdata" )) {
            HealthConnectClient.SDK_AVAILABLE -> "Available"
            HealthConnectClient.SDK_UNAVAILABLE -> "NotSupported"
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> "NotInstalled"
            else -> throw RuntimeException("Invalid sdk status: $status")
        }

        val res = JSObject().apply {
            put("availability", availability)
        }
        call.resolve(res)
    }

    @PluginMethod
    fun requestHealthPermissions(call: PluginCall) {
        if (HealthConnectClient.getSdkStatus(this.context) == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.android.vending")
            intent.data = Uri.parse("market://details")
                .buildUpon()
                .appendQueryParameter("id", "com.google.android.apps.healthdata")
                .appendQueryParameter("url", "healthconnect://onboarding")
                .build()
            intent.putExtra("overlay", true)
            intent.putExtra("callerId", context.packageName)
            startActivityForResult(call, intent, "handleInstalled")
            return
        }

        val readPermissions = call.getArray("read").toList<String>().map {
            HealthPermission.getReadPermission(
                recordType = RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            )
        }.toSet()

        val writePermissions = call.getArray("write").toList<String>().map {
            HealthPermission.getWritePermission(
                recordType = RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            )
        }.toSet()

        val intent = permissionContract.createIntent(
            this.context,
            readPermissions + writePermissions
        )

        startActivityForResult(call, intent, "handleRequestPermission")
    }

    @ActivityCallback
    fun handleInstalled(call: PluginCall, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            requestHealthPermissions(call)
        } else {
            call.errorCallback("canceled")
        }
    }

    @ActivityCallback
    fun handleRequestPermission(call: PluginCall, result: ActivityResult) {
        val reqReadPermissions = call.getArray("read").toList<String>().map {
            HealthPermission.getReadPermission(
                recordType = RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            )
        }.toSet()
        val reqWritePermissions = call.getArray("write").toList<String>().map {
            HealthPermission.getWritePermission(
                recordType = RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            )
        }.toSet()

        val grantedPermissions = permissionContract.parseResult(result.resultCode, result.data).toSet()
        val hasAllPermissions = grantedPermissions.containsAll(reqReadPermissions + reqWritePermissions)

        val res = JSObject().apply {
            put("grantedPermissions", JSArray(grantedPermissions))
            put("hasAllPermissions", hasAllPermissions)
        }
        call.resolve(res)
    }

    @PluginMethod
    fun checkHealthPermissions(call: PluginCall) {
        this.activity.lifecycleScope.launch {
            val reqReadPermissions = call.getArray("read").toList<String>().map {
                HealthPermission.getReadPermission(
                    recordType = RECORDS_TYPE_NAME_MAP[it]
                        ?: throw IllegalArgumentException("Unexpected RecordType: $it")
                )
            }.toSet()
            val reqWritePermissions = call.getArray("write").toList<String>().map {
                HealthPermission.getWritePermission(
                    recordType = RECORDS_TYPE_NAME_MAP[it]
                        ?: throw IllegalArgumentException("Unexpected RecordType: $it")
                )
            }.toSet()

            val grantedPermissions = healthConnectClient.permissionController.getGrantedPermissions()
            val hasAllPermissions = grantedPermissions.containsAll(reqReadPermissions + reqWritePermissions)

            val res = JSObject().apply {
                put("grantedPermissions", JSArray(grantedPermissions))
                put("hasAllPermissions", hasAllPermissions)
            }
            call.resolve(res)
        }
    }

    @PluginMethod
    fun revokeHealthPermissions(call: PluginCall) {
        this.activity.lifecycleScope.launch {
            healthConnectClient.permissionController.revokeAllPermissions()
            call.resolve()
        }
    }

    @PluginMethod
    fun openHealthConnectSetting(call: PluginCall) {
        val action = HealthConnectClient.ACTION_HEALTH_CONNECT_SETTINGS
        val intent = Intent(action)
        this.context.startActivity(intent)

        call.resolve()
    }

    @PluginMethod
    fun insertRecords(call: PluginCall) {
        this.activity.lifecycleScope.launch {
            val records = call.getArray("records").toList<JSONObject>().map { it.toRecord() }
            val result = healthConnectClient.insertRecords(records)

            val res = JSObject().apply {
                put("recordIds", result.recordIdsList)
            }
            call.resolve(res)
        }
    }

    @PluginMethod
    fun getRecord(call: PluginCall) {
        this.activity.lifecycleScope.launch {
            val type = call.getString("type").let {
                RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            }

            val result = healthConnectClient.readRecord(
                recordType = type,
                recordId = requireNotNull(call.getString("recordId"))
            )

            val res = JSObject().apply {
                this.put("record", result.record.toJSONObject())
            }
            call.resolve(res)
        }
    }

    @PluginMethod
    fun getRecords(call: PluginCall) {
        this.activity.lifecycleScope.launch {
            val type = call.getString("type").let {
                RECORDS_TYPE_NAME_MAP[it] ?: throw IllegalArgumentException("Unexpected RecordType: $it")
            }
            val request = ReadRecordsRequest(
                recordType = type,
                timeRangeFilter = call.data.getTimeRangeFilter("timeRangeFilter"),
                dataOriginFilter = call.data.getDataOriginFilter("dataOriginFilter"),
                ascendingOrder = call.getBoolean("ascendingOrder") ?: true,
                pageSize = call.getInt("pageSize") ?: 1000,
                pageToken = call.getString("pageToken"),
            )
            val result = healthConnectClient.readRecords(request)

            val res = JSObject().apply {
                val records = result.records.map { it.toJSONObject() }.toJSONArray()
                this.put("records", records)
                this.put("pageToken", result.pageToken)
            }
            call.resolve(res)
        }

    }









}
