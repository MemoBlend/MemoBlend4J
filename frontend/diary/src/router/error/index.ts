import type { RouteRecordRaw } from "vue-router";
import ErrorView from '@/views/error/ErrorView.vue';
import { fa } from "vuetify/locale";

export const errorRoutes: RouteRecordRaw[] = [
  {
    path: '/error',
    name: 'error',
    component: ErrorView,
    meta: { requiresAuth: false }
  }
]
