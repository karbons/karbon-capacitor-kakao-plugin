export interface CapacitorKakaoPlugin {
  /**
   * 카카오 로그인
   */
  kakaoLogin(): Promise<KakaoLoginResult>;

  /**
   * 카카오 로그아웃
   */
  logout(): Promise<void>;

  /**
   * 사용자 정보 가져오기
   */
  getUserInfo(): Promise<any>;
}

export interface KakaoLoginResult {
  accessToken: string;
  refreshToken: string;
  userId?: string;
  email?: string;
  nickname?: string;
}
