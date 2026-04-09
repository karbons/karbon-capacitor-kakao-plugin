# karbon-capacitor-kakao-plugin

기존 `capacitor-kakao-plugin`을 최신 **Capacitor 7 및 8** 버전에 호환되도록 수정한 버전입니다.  
본 플러그인은 **카본빌더(Karbon Builder) Svelte 프론트엔드** 환경에서 원활하게 이용하기 위해 제작되었습니다.

## 주요 변경 사항
- Capacitor 7 & 8 정식 지원
- 최신 Android/iOS 환경 대응


## Installation

```bash
npm install karbon-capacitor-kakao-plugin
npx cap sync
```

## Usage

```typescript
import { KakaoLogin } from 'karbon-capacitor-kakao-plugin';

// Example usage
const login = async () => {
  const result = await KakaoLogin.login();
  console.log(result);
};
```

## License

MIT
