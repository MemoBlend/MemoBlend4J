import { defineStore } from 'pinia';
import { userApi, authControllerApi } from '@/api-client';
import type { LoginRequest } from '@/generated/api-client/models';

/**
 * 認証状態のストアです。
 */
export const useAuthenticationStore = defineStore('authentication', {
  state: () => ({
    authenticationState: JSON.parse(
      sessionStorage.getItem('isAuthenticated') || 'false',
    ) as boolean,
    nameState: JSON.parse(sessionStorage.getItem('name') || '""') as string,
    tokenState: sessionStorage.getItem('jwt_token') || '',
    usernameState: sessionStorage.getItem('username') || '',
  }),
  actions: {
    /**
     * JWTトークンを使用してアプリケーションにログインします。
     * セッションストレージに認証状態とトークンを保存します。
     * @param authId 認証ID
     * @param password パスワード
     */
    async signInAsync(authId: string, password: string) {
      const loginRequest: LoginRequest = {
        authId: authId,
        password: password,
      };

      const response = await authControllerApi.login(loginRequest);

      if (response.data.token) {
        this.tokenState = response.data.token;
        this.usernameState = response.data.username || '';
        this.nameState = response.data.username || '';
        this.authenticationState = true;

        // セッションストレージに保存
        sessionStorage.setItem('jwt_token', this.tokenState);
        sessionStorage.setItem('username', this.usernameState);
        sessionStorage.setItem('name', JSON.stringify(this.nameState));
        sessionStorage.setItem(
          'isAuthenticated',
          JSON.stringify(this.authenticationState),
        );
      } else {
        throw new Error(
          'ログインに失敗しました。トークンが取得できませんでした。',
        );
      }
    },
    /**
     * アプリケーションからログアウトします。
     * セッションストレージから認証状態とトークンを削除します。
     */
    async signOutAsync() {
      this.nameState = '';
      this.usernameState = '';
      this.tokenState = '';
      this.authenticationState = false;
      sessionStorage.removeItem('isAuthenticated');
      sessionStorage.removeItem('name');
      sessionStorage.removeItem('jwt_token');
      sessionStorage.removeItem('username');
    },
    /**
     * セッションストレージからトークンを復元し、有効性をチェックします。
     */
    async restoreSession() {
      const token = sessionStorage.getItem('jwt_token');
      if (token) {
        this.tokenState = token;
        this.usernameState = sessionStorage.getItem('username') || '';
        this.nameState = JSON.parse(
          sessionStorage.getItem('name') || '""',
        ) as string;
        this.authenticationState = true;

        // トークンの有効性をチェック
        try {
          await this.validateToken();
        } catch (error) {
          // トークンが無効な場合はログアウト
          await this.signOutAsync();
          throw new Error('セッションが無効です。再度ログインしてください。');
        }
      }
    },
    /**
     * JWTトークンの有効性を検証します。
     */
    async validateToken() {
      if (!this.tokenState) {
        throw new Error('トークンが存在しません。');
      }

      try {
        // 認証が必要なエンドポイントを呼び出してトークンの有効性を確認
        // ここではユーザー情報を取得して有効性を確認
        await userApi.getUser(1);
      } catch (error) {
        throw new Error('トークンが無効です。');
      }
    },
  },
  getters: {
    /**
     * ユーザー名を取得します。
     * @param state 状態。
     * @returns ユーザー名。
     */
    name(state) {
      return state.nameState;
    },
    /**
     * ユーザー名を取得します。
     * @param state 状態。
     * @returns ユーザー名。
     */
    username(state) {
      return state.usernameState;
    },
    /**
     * JWTトークンを取得します。
     * @param state 状態。
     * @returns JWTトークン。
     */
    token(state) {
      return state.tokenState;
    },
    /**
     * ユーザーが認証済みかどうかを取得します。
     * @param state 状態。
     * @returns 認証済みかどうかを表す真理値。
     */
    isAuthenticated(state) {
      return state.authenticationState && !!state.tokenState;
    },
  },
});
