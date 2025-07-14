import { useAuthenticationStore } from '@/stores/authentication';

/**
 * JWTトークンを使用してサインインします。
 * @param authId 認証ID
 * @param password パスワード
 */
export async function signInAsync(authId: string, password: string) {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.signInAsync(authId, password);
}

/**
 * サインアウトします。
 */
export async function signOutAsync() {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.signOutAsync();
}

/**
 * セッションを復元します。
 */
export async function restoreSessionAsync() {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.restoreSession();
}

/**
 * JWTトークンの有効性を検証します。
 */
export async function validateTokenAsync() {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.validateToken();
}
