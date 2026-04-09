import { WebPlugin } from '@capacitor/core';
import type { CapacitorKakaoPlugin, KakaoLoginResult } from './definitions';

export class KarbonCapacitorKakaoWeb extends WebPlugin implements CapacitorKakaoPlugin {
  async kakaoLogin(): Promise<KakaoLoginResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  async logout(): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getUserInfo(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }
}
