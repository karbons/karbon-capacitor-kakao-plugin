# karbon-capacitor-kakao-plugin

Kakao Login plugin for Capacitor 7 & 8.

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
