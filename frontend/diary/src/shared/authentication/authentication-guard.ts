import { useAuthenticationStore } from '@/stores/authentication';
import type { Router } from 'vue-router';

/**
 * ナビゲーションガードです。
 * 画面遷移する際に共通して実行する処理を定義します。
 * たとえば、未認証の場合はログイン画面に遷移させます。
 * @param router vue-router。
 */
export const authenticationGuard = (router: Router) => {
  const authenticationStore = useAuthenticationStore();
  router.beforeEach((to) => {
    if (to.meta.requiresAuth && !authenticationStore.isAuthenticated) {
      return { name: 'login' };
    }
    return true;
  });
};
