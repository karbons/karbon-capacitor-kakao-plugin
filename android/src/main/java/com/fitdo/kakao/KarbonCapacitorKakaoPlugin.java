package com.fitdo.kakao;

import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.ClientError;
import com.kakao.sdk.common.model.ClientErrorCause;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

@CapacitorPlugin(name = "KarbonCapacitorKakaoPlugin")
public class KarbonCapacitorKakaoPlugin extends Plugin {

    private static final String TAG = "KarbonKakaoPlugin";

    @PluginMethod
    public void kakaoLogin(PluginCall call) {
        getActivity().runOnUiThread(() -> {
            Function2<OAuthToken, Throwable, Unit> callback = (oauthToken, error) -> {
                if (error != null) {
                    call.reject(error.getMessage());
                    return Unit.INSTANCE;
                }
                handleLoginSuccess(oauthToken, call);
                return Unit.INSTANCE;
            };

            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(getContext())) {
                UserApiClient.getInstance().loginWithKakaoTalk(getActivity(), (oauthToken, error) -> {
                    if (error != null) {
                        if (error instanceof ClientError && ((ClientError) error).getReason() == ClientErrorCause.Cancelled) {
                            call.reject("user_canceled");
                        } else {
                            UserApiClient.getInstance().loginWithKakaoAccount(getActivity(), callback);
                        }
                        return Unit.INSTANCE;
                    }
                    handleLoginSuccess(oauthToken, call);
                    return Unit.INSTANCE;
                });
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(getActivity(), callback);
            }
        });
    }

    private void handleLoginSuccess(OAuthToken oauthToken, PluginCall call) {
        UserApiClient.getInstance().me((user, error) -> {
            JSObject result = new JSObject();
            result.put("accessToken", oauthToken.getAccessToken());
            result.put("refreshToken", oauthToken.getRefreshToken() != null ? oauthToken.getRefreshToken() : "");

            if (user != null) {
                if (user.getId() != null) result.put("userId", String.valueOf(user.getId()));
                if (user.getKakaoAccount() != null) {
                    if (user.getKakaoAccount().getEmail() != null) result.put("email", user.getKakaoAccount().getEmail());
                    if (user.getKakaoAccount().getProfile() != null) {
                        if (user.getKakaoAccount().getProfile().getNickname() != null)
                            result.put("nickname", user.getKakaoAccount().getProfile().getNickname());
                    }
                }
            }
            call.resolve(result);
            return Unit.INSTANCE;
        });
    }

    @PluginMethod
    public void logout(PluginCall call) {
        UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                call.reject(error.getMessage());
                return Unit.INSTANCE;
            }
            call.resolve();
            return Unit.INSTANCE;
        });
    }

    @PluginMethod
    public void getUserInfo(PluginCall call) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                call.reject(error.getMessage());
                return Unit.INSTANCE;
            }
            if (user == null) {
                call.reject("User not found");
                return Unit.INSTANCE;
            }
            JSObject result = new JSObject();
            if (user.getId() != null) result.put("id", String.valueOf(user.getId()));
            if (user.getKakaoAccount() != null) {
                if (user.getKakaoAccount().getEmail() != null) result.put("email", user.getKakaoAccount().getEmail());
                if (user.getKakaoAccount().getProfile() != null) {
                    if (user.getKakaoAccount().getProfile().getNickname() != null)
                        result.put("nickname", user.getKakaoAccount().getProfile().getNickname());
                }
            }
            call.resolve(result);
            return Unit.INSTANCE;
        });
    }
}
