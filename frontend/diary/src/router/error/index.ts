import type { RouteRecordRaw } from "vue-router";
import ErrorView from '@/views/error/ErrorView.vue';

export const errorRoutes: RouteRecordRaw[] = [
  {
    path: '/error',
    name: 'error',
    component: ErrorView
  }
]
