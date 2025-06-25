import type { RouteRecordRaw } from 'vue-router';

export const scheduleRoutes: RouteRecordRaw[] = [
  {
    path: '/schedule/suggest',
    name: 'scheduleSuggest',
    component: () => import('@/views/schedule/ScheduleSuggestView.vue'),
    meta: {
      requiresAuth: true,
    },
  },
];
