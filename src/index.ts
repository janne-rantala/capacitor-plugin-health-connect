import { registerPlugin } from '@capacitor/core';

import type { HealthConnectPlugin } from './definitions';

const HealthConnect = registerPlugin<HealthConnectPlugin>('HealthConnect', {
  web: () => import('./web').then(m => new m.HealthConnectWeb()),
});

export * from './definitions';
export { HealthConnect };
