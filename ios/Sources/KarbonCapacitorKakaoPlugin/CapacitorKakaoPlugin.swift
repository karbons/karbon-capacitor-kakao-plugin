import Foundation
import Capacitor
import KakaoSDKAuth
import KakaoSDKUser
import KakaoSDKCommon

@objc(KarbonCapacitorKakaoPlugin)
public class KarbonCapacitorKakaoPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "KarbonCapacitorKakaoPlugin"
    public let jsName = "KarbonCapacitorKakaoPlugin"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "kakaoLogin", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logout", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getUserInfo", returnType: CAPPluginReturnPromise)
    ]

    @objc func kakaoLogin(_ call: CAPPluginCall) {
        if UserApi.isKakaoTalkLoginAvailable() {
            UserApi.shared.loginWithKakaoTalk { (oauthToken, error) in
                if let error = error {
                    call.reject(error.localizedDescription)
                    return
                }
                self.handleLoginSuccess(oauthToken: oauthToken!, call: call)
            }
        } else {
            UserApi.shared.loginWithKakaoAccount { (oauthToken, error) in
                if let error = error {
                    call.reject(error.localizedDescription)
                    return
                }
                self.handleLoginSuccess(oauthToken: oauthToken!, call: call)
            }
        }
    }

    private func handleLoginSuccess(oauthToken: OAuthToken, call: CAPPluginCall) {
        UserApi.shared.me { (user, error) in
            var result: [String: Any] = [
                "accessToken": oauthToken.accessToken,
                "refreshToken": oauthToken.refreshToken ?? ""
            ]
            if let user = user {
                if let id = user.id { result["userId"] = String(id) }
                if let email = user.kakaoAccount?.email { result["email"] = email }
                if let nickname = user.kakaoAccount?.profile?.nickname { result["nickname"] = nickname }
            }
            call.resolve(result)
        }
    }

    @objc func logout(_ call: CAPPluginCall) {
        UserApi.shared.logout { (error) in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        }
    }

    @objc func getUserInfo(_ call: CAPPluginCall) {
        UserApi.shared.me { (user, error) in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            guard let user = user else {
                call.reject("User not found")
                return
            }
            var result: [String: Any] = [:]
            if let id = user.id { result["id"] = String(id) }
            if let email = user.kakaoAccount?.email { result["email"] = email }
            if let nickname = user.kakaoAccount?.profile?.nickname { result["nickname"] = nickname }
            call.resolve(result)
        }
    }
}
