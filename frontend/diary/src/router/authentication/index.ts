import type { RouteRecordRaw } from 'vue-router';

export const authenticationRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/authentication/LoginView.vue'),
    meta: {
      requiresAuth: false,
    },
  },
];
