import { useAuthenticationStore } from '@/stores/authentication';
import type { Router, RouteRecordName } from 'vue-router';

/**
 * ナビゲーションガードです。
 * 画面遷移する際に共通して実行する処理を定義します。
 * たとえば、未認証の場合はログイン画面に遷移させます。
 * @param router vue-router。
 */
export const authenticationGuard = (router: Router) => {
  const authenticationStore = useAuthenticationStore();

  router.beforeEach((to) => {
    const ignoreAuthPaths: (RouteRecordName | null | undefined)[] = [
      'login',
      'error',
    ];
    if (ignoreAuthPaths.includes(to.name)) {
      return true;
    }

    if (authenticationStore.isAuthenticated) {
      return true;
    }

    return { name: 'login' };
  });
};
