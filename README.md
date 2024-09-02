# capacitor-plugin-health-connect

The Health Connect app is responsible for handling all the requests that your application sends through the Health Connect SDK. These requests include storing data and managing its read and write access.

## Install

```bash
npm install capacitor-plugin-health-connect
npx cap sync
```

## API

<docgen-index>

* [`checkAvailabilityAndConnect()`](#checkavailabilityandconnect)
* [`insertRecords(...)`](#insertrecords)
* [`getRecord(...)`](#getrecord)
* [`getRecords(...)`](#getrecords)
* [`getChangesToken(...)`](#getchangestoken)
* [`getChanges(...)`](#getchanges)
* [`requestHealthPermissions(...)`](#requesthealthpermissions)
* [`checkHealthPermissions(...)`](#checkhealthpermissions)
* [`revokeHealthPermissions()`](#revokehealthpermissions)
* [`openHealthConnectSetting()`](#openhealthconnectsetting)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkAvailabilityAndConnect()

```typescript
checkAvailabilityAndConnect() => Promise<{ availability: HealthConnectAvailability; }>
```

**Returns:** <code>Promise&lt;{ availability: <a href="#healthconnectavailability">HealthConnectAvailability</a>; }&gt;</code>

--------------------


### insertRecords(...)

```typescript
insertRecords(options: { records: Record[]; }) => Promise<{ recordIds: string[]; }>
```

| Param         | Type                                |
| ------------- | ----------------------------------- |
| **`options`** | <code>{ records: Record[]; }</code> |

**Returns:** <code>Promise&lt;{ recordIds: string[]; }&gt;</code>

--------------------


### getRecord(...)

```typescript
getRecord(options: { type: RecordType; recordId: string; }) => Promise<{ record: StoredRecord; }>
```

| Param         | Type                                                                           |
| ------------- | ------------------------------------------------------------------------------ |
| **`options`** | <code>{ type: <a href="#recordtype">RecordType</a>; recordId: string; }</code> |

**Returns:** <code>Promise&lt;{ record: <a href="#storedrecord">StoredRecord</a>; }&gt;</code>

--------------------


### getRecords(...)

```typescript
getRecords(options: { type: RecordType; timeRangeFilter: TimeRangeFilter; dataOriginFilter?: string[]; ascendingOrder?: boolean; pageSize?: number; pageToken?: string; }) => Promise<{ records: StoredRecord[]; pageToken?: string; }>
```

| Param         | Type                                                                                                                                                                                                                        |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ type: <a href="#recordtype">RecordType</a>; timeRangeFilter: <a href="#timerangefilter">TimeRangeFilter</a>; dataOriginFilter?: string[]; ascendingOrder?: boolean; pageSize?: number; pageToken?: string; }</code> |

**Returns:** <code>Promise&lt;{ records: StoredRecord[]; pageToken?: string; }&gt;</code>

--------------------


### getChangesToken(...)

```typescript
getChangesToken(options: { types: RecordType[]; }) => Promise<{ token: string; }>
```

| Param         | Type                                  |
| ------------- | ------------------------------------- |
| **`options`** | <code>{ types: RecordType[]; }</code> |

**Returns:** <code>Promise&lt;{ token: string; }&gt;</code>

--------------------


### getChanges(...)

```typescript
getChanges(options: { token: string; }) => Promise<{ changes: Change[]; nextToken: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

**Returns:** <code>Promise&lt;{ changes: Change[]; nextToken: string; }&gt;</code>

--------------------


### requestHealthPermissions(...)

```typescript
requestHealthPermissions(options: { read: RecordType[]; write: RecordType[]; }) => Promise<{ grantedPermissions: string[]; hasAllPermissions: boolean; }>
```

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code>{ read: RecordType[]; write: RecordType[]; }</code> |

**Returns:** <code>Promise&lt;{ grantedPermissions: string[]; hasAllPermissions: boolean; }&gt;</code>

--------------------


### checkHealthPermissions(...)

```typescript
checkHealthPermissions(options: { read: RecordType[]; write: RecordType[]; }) => Promise<{ grantedPermissions: string[]; hasAllPermissions: boolean; }>
```

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code>{ read: RecordType[]; write: RecordType[]; }</code> |

**Returns:** <code>Promise&lt;{ grantedPermissions: string[]; hasAllPermissions: boolean; }&gt;</code>

--------------------


### revokeHealthPermissions()

```typescript
revokeHealthPermissions() => Promise<void>
```

--------------------


### openHealthConnectSetting()

```typescript
openHealthConnectSetting() => Promise<void>
```

--------------------


### Interfaces


#### Date

Enables basic storage and retrieval of dates and times.

| Method                 | Signature                                                                                                    | Description                                                                                                                             |
| ---------------------- | ------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------- |
| **toString**           | () =&gt; string                                                                                              | Returns a string representation of a date. The format of the string depends on the locale.                                              |
| **toDateString**       | () =&gt; string                                                                                              | Returns a date as a string value.                                                                                                       |
| **toTimeString**       | () =&gt; string                                                                                              | Returns a time as a string value.                                                                                                       |
| **toLocaleString**     | () =&gt; string                                                                                              | Returns a value as a string value appropriate to the host environment's current locale.                                                 |
| **toLocaleDateString** | () =&gt; string                                                                                              | Returns a date as a string value appropriate to the host environment's current locale.                                                  |
| **toLocaleTimeString** | () =&gt; string                                                                                              | Returns a time as a string value appropriate to the host environment's current locale.                                                  |
| **valueOf**            | () =&gt; number                                                                                              | Returns the stored time value in milliseconds since midnight, January 1, 1970 UTC.                                                      |
| **getTime**            | () =&gt; number                                                                                              | Gets the time value in milliseconds.                                                                                                    |
| **getFullYear**        | () =&gt; number                                                                                              | Gets the year, using local time.                                                                                                        |
| **getUTCFullYear**     | () =&gt; number                                                                                              | Gets the year using Universal Coordinated Time (UTC).                                                                                   |
| **getMonth**           | () =&gt; number                                                                                              | Gets the month, using local time.                                                                                                       |
| **getUTCMonth**        | () =&gt; number                                                                                              | Gets the month of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                             |
| **getDate**            | () =&gt; number                                                                                              | Gets the day-of-the-month, using local time.                                                                                            |
| **getUTCDate**         | () =&gt; number                                                                                              | Gets the day-of-the-month, using Universal Coordinated Time (UTC).                                                                      |
| **getDay**             | () =&gt; number                                                                                              | Gets the day of the week, using local time.                                                                                             |
| **getUTCDay**          | () =&gt; number                                                                                              | Gets the day of the week using Universal Coordinated Time (UTC).                                                                        |
| **getHours**           | () =&gt; number                                                                                              | Gets the hours in a date, using local time.                                                                                             |
| **getUTCHours**        | () =&gt; number                                                                                              | Gets the hours value in a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                       |
| **getMinutes**         | () =&gt; number                                                                                              | Gets the minutes of a <a href="#date">Date</a> object, using local time.                                                                |
| **getUTCMinutes**      | () =&gt; number                                                                                              | Gets the minutes of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                           |
| **getSeconds**         | () =&gt; number                                                                                              | Gets the seconds of a <a href="#date">Date</a> object, using local time.                                                                |
| **getUTCSeconds**      | () =&gt; number                                                                                              | Gets the seconds of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                           |
| **getMilliseconds**    | () =&gt; number                                                                                              | Gets the milliseconds of a <a href="#date">Date</a>, using local time.                                                                  |
| **getUTCMilliseconds** | () =&gt; number                                                                                              | Gets the milliseconds of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                      |
| **getTimezoneOffset**  | () =&gt; number                                                                                              | Gets the difference in minutes between the time on the local computer and Universal Coordinated Time (UTC).                             |
| **setTime**            | (time: number) =&gt; number                                                                                  | Sets the date and time value in the <a href="#date">Date</a> object.                                                                    |
| **setMilliseconds**    | (ms: number) =&gt; number                                                                                    | Sets the milliseconds value in the <a href="#date">Date</a> object using local time.                                                    |
| **setUTCMilliseconds** | (ms: number) =&gt; number                                                                                    | Sets the milliseconds value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                              |
| **setSeconds**         | (sec: number, ms?: number \| undefined) =&gt; number                                                         | Sets the seconds value in the <a href="#date">Date</a> object using local time.                                                         |
| **setUTCSeconds**      | (sec: number, ms?: number \| undefined) =&gt; number                                                         | Sets the seconds value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                   |
| **setMinutes**         | (min: number, sec?: number \| undefined, ms?: number \| undefined) =&gt; number                              | Sets the minutes value in the <a href="#date">Date</a> object using local time.                                                         |
| **setUTCMinutes**      | (min: number, sec?: number \| undefined, ms?: number \| undefined) =&gt; number                              | Sets the minutes value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                   |
| **setHours**           | (hours: number, min?: number \| undefined, sec?: number \| undefined, ms?: number \| undefined) =&gt; number | Sets the hour value in the <a href="#date">Date</a> object using local time.                                                            |
| **setUTCHours**        | (hours: number, min?: number \| undefined, sec?: number \| undefined, ms?: number \| undefined) =&gt; number | Sets the hours value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                     |
| **setDate**            | (date: number) =&gt; number                                                                                  | Sets the numeric day-of-the-month value of the <a href="#date">Date</a> object using local time.                                        |
| **setUTCDate**         | (date: number) =&gt; number                                                                                  | Sets the numeric day of the month in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                        |
| **setMonth**           | (month: number, date?: number \| undefined) =&gt; number                                                     | Sets the month value in the <a href="#date">Date</a> object using local time.                                                           |
| **setUTCMonth**        | (month: number, date?: number \| undefined) =&gt; number                                                     | Sets the month value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                     |
| **setFullYear**        | (year: number, month?: number \| undefined, date?: number \| undefined) =&gt; number                         | Sets the year of the <a href="#date">Date</a> object using local time.                                                                  |
| **setUTCFullYear**     | (year: number, month?: number \| undefined, date?: number \| undefined) =&gt; number                         | Sets the year value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                      |
| **toUTCString**        | () =&gt; string                                                                                              | Returns a date converted to a string using Universal Coordinated Time (UTC).                                                            |
| **toISOString**        | () =&gt; string                                                                                              | Returns a date as a string value in ISO format.                                                                                         |
| **toJSON**             | (key?: any) =&gt; string                                                                                     | Used by the JSON.stringify method to enable the transformation of an object's data for JavaScript Object Notation (JSON) serialization. |


### Type Aliases


#### HealthConnectAvailability

<code>'Available' | 'NotInstalled' | 'NotSupported'</code>


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### StoredRecord

<code><a href="#recordbase">RecordBase</a> & <a href="#record">Record</a></code>


#### RecordBase

<code>{ metadata: <a href="#recordmetadata">RecordMetadata</a>; }</code>


#### RecordMetadata

<code>{ id: string; clientRecordId?: string; clientRecordVersion: number; lastModifiedTime: <a href="#date">Date</a>; dataOrigin: string; }</code>


#### RecordType

<code>'ActiveCaloriesBurned' | 'BasalBodyTemperature' | 'BasalMetabolicRate' | 'BloodGlucose' | 'BloodPressure' | 'BodyFat' | 'BodyTemperature' | 'HeartRateSeries' | 'Height' | 'OxygenSaturation' | 'RespiratoryRate' | 'RestingHeartRate' | 'Steps' | 'Weight'</code>


#### TimeRangeFilter

<code>{ type: 'before' | 'after'; time: <a href="#date">Date</a>; } | { type: 'between'; startTime: <a href="#date">Date</a>; endTime: <a href="#date">Date</a>; }</code>


#### Change

<code>{ type: 'Upsert'; record: <a href="#record">Record</a>; } | { type: 'Delete'; recordId: string; }</code>

</docgen-api>
