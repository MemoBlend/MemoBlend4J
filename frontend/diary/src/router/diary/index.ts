import type { RouteRecordRaw } from 'vue-router';

export const diaryRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'diaries',
    component: () => import('@/views/diary/DiariesView.vue'),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/detail/:id',
    name: 'detail',
    component: () => import('@/views/diary/DiaryDetailView.vue'),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/detail/:id/edit',
    name: 'edit',
    component: () => import('@/views/diary/DiaryEditView.vue'),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/create',
    name: 'create',
    component: () => import('@/views/diary/DiaryCreateView.vue'),
    meta: {
      requiresAuth: true,
    },
  },
];
