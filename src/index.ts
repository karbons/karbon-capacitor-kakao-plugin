import { registerPlugin } from '@capacitor/core';

import type { CapacitorKakaoPlugin } from './definitions';

const KarbonCapacitorKakao = registerPlugin<CapacitorKakaoPlugin>('KarbonCapacitorKakaoPlugin', {
  web: () => import('./web').then((m) => new m.KarbonCapacitorKakaoWeb()),
});

export * from './definitions';
export { KarbonCapacitorKakao };
