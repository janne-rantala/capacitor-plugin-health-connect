import { Component } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { HealthConnect } from 'capacitor-plugin-health-connect';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  message: string = '';
  permissionsGranted: boolean = false;

  constructor(private alertController: AlertController) {}

  async checkAvailability() {
    try {
      const availability = await HealthConnect.checkAvailabilityAndConnect();
      this.message = `Availability: ${availability.availability}`;

      if (availability.availability === 'Available') {
        this.requestPermissions();
      } else if (availability.availability === 'NotInstalled') {
        this.showAlert('Health Connect Not Installed', 'Please install Health Connect.', async () => {
          await HealthConnect.requestHealthPermissions({
            read: ['Steps'],
            write: ['Steps'],
          });
        });
      }
    } catch (error: any) {
      this.message = `Error: ${error.message}`;
    }
  }

  async requestPermissions() {
    try {
      const permissionsResult = await HealthConnect.requestHealthPermissions({
        read: ['Steps'],
        write: ['Steps'],
      });

      this.message = `Permissions Granted: ${permissionsResult.hasAllPermissions}`;
      this.permissionsGranted = permissionsResult.hasAllPermissions;

      if (!permissionsResult.hasAllPermissions) {
        this.showAlert('Permissions Required', 'You did not grant the required permissions. Please retry or open settings.', async () => {
          await this.checkHealthPermissions();
        });
      } else {
        this.showAlert('Permissions Granted', 'You have successfully granted the required permissions.');
      }
    } catch (error: any) {
      this.message = `Error: ${error.message}`;
    }
  }

  async checkHealthPermissions() {
    try {
      const permissionsStatus = await HealthConnect.checkHealthPermissions({
        read: ['Steps'],
        write: ['Steps'],
      });

      if (permissionsStatus.hasAllPermissions) {
        this.permissionsGranted = true;
        this.showAlert('Permissions Granted', 'You have successfully granted the required permissions.');
      } else {
        this.showAlert('Permissions Required', 'Permissions are still not granted. Please open settings.', async () => {
          await HealthConnect.openHealthConnectSetting();
        });
      }
    } catch (error: any) {
      this.message = `Error: ${error.message}`;
    }
  }

  async insertStepsRecord() {
    if (!this.permissionsGranted) {
      await this.showAlert('Permission Required', 'Please grant permissions before inserting records.');
      return;
    }

    try {
      const startTime = new Date();
      startTime.setHours(startTime.getHours() - 1);
      const endTime = new Date();

      const result = await HealthConnect.insertRecords({
        records: [{
          type: 'Steps',
          startTime: startTime,
          endTime: endTime,
          count: 1000
        }]
      });
      this.message = `Inserted Step ID: ${result.recordIds}`;
    } catch (error: any) {
      console.error('Error inserting steps record:', error);
      this.message = `Error: ${error.message}`;
    }
  }

  async readStepsRecords() {
    if (!this.permissionsGranted) {
      await this.showAlert('Permission Required', 'Please grant permissions before reading records.');
      return;
    }

    try {
      const records = await HealthConnect.getRecords({
        type: 'Steps',
        timeRangeFilter: {
          type: 'between',
          startTime: new Date('2024-08-01T00:00:00Z'),
          endTime: new Date(),
        }
      });
      this.message = `Steps Count: ${records.records.length}`;
    } catch (error: any) {
      this.message = `Error: ${error.message}`;
    }
  }

  async openSettings() {
    try {
      await HealthConnect.openHealthConnectSetting();
      this.message = 'Opened Health Connect Settings';
    } catch (error: any) {
      this.message = `Error: ${error.message}`;
    }
  }

  async showAlert(header: string, message: string, callback?: () => void) {
    const alert = await this.alertController.create({
      header,
      message,
      buttons: [
        {
          text: 'OK',
          handler: () => {
            if (callback) {
              callback();
            }
          }
        }
      ]
    });

    await alert.present();
  }
}
