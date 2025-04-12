import LoginView from "@/views/authentication/LoginView.vue";
import type { RouteRecordRaw } from "vue-router";

export const authenticationRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  }
]
