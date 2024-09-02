import { WebPlugin } from '@capacitor/core';

import type { Change, HealthConnectAvailability, HealthConnectPlugin, Record, RecordMetadata, RecordType, TimeRangeFilter } from './definitions';

export class HealthConnectWeb extends WebPlugin implements HealthConnectPlugin {
  checkAvailabilityAndConnect(): Promise<{ availability: HealthConnectAvailability; }> {
    throw new Error('Method not implemented.');
  }
  insertRecords(_options: { records: Record[]; }): Promise<{ recordIds: string[]; }> {
    throw new Error('Method not implemented.');
  }
  getRecord(_options: { type: RecordType; recordId: string; }): Promise<{ record: { metadata: RecordMetadata; } & Record; }> {
    throw new Error('Method not implemented.');
  }
  getRecords(_options: { type: RecordType; timeRangeFilter: TimeRangeFilter; dataOriginFilter?: string[]; ascendingOrder?: boolean; pageSize?: number; pageToken?: string; }): Promise<{ records: ({ metadata: RecordMetadata; } & Record)[]; pageToken?: string; }> {
    throw new Error('Method not implemented.');
  }
  getChangesToken(_options: { types: RecordType[]; }): Promise<{ token: string; }> {
    throw new Error('Method not implemented.');
  }
  getChanges(_options: { token: string; }): Promise<{ changes: Change[]; nextToken: string; }> {
    throw new Error('Method not implemented.');
  }
  requestHealthPermissions(_options: { read: RecordType[]; write: RecordType[]; }): Promise<{ grantedPermissions: string[]; hasAllPermissions: boolean; }> {
    throw new Error('Method not implemented.');
  }
  checkHealthPermissions(_options: { read: RecordType[]; write: RecordType[]; }): Promise<{ grantedPermissions: string[]; hasAllPermissions: boolean; }> {
    throw new Error('Method not implemented.');
  }
  revokeHealthPermissions(): Promise<void> {
    throw new Error('Method not implemented.');
  }
  openHealthConnectSetting(): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
