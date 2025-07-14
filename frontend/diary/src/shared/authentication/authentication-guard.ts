import { useAuthenticationStore } from '@/stores/authentication';
import { validateTokenAsync } from '@/services/authentication/authentication-service';
import type { Router } from 'vue-router';

/**
 * ナビゲーションガードです。
 * 画面遷移する際に共通して実行する処理を定義します。
 * たとえば、未認証の場合やトークンが無効な場合はログイン画面に遷移させます。
 * @param router vue-router。
 */
export const authenticationGuard = (router: Router) => {
  const authenticationStore = useAuthenticationStore();

  router.beforeEach(async (to) => {
    if (to.meta.requiresAuth) {
      if (!authenticationStore.isAuthenticated) {
        return { name: 'login' };
      }

      // トークンの有効性をチェック
      try {
        await validateTokenAsync();
      } catch (error) {
        console.warn('トークンが無効です:', error);
        return { name: 'login' };
      }
    }
    return true;
  });
};
