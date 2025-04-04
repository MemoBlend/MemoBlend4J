import LoginView from "@/views/authentication/LoginView.vue";
import RegistrationView from "@/views/authentication/RegistrationView.vue";
import type { RouteRecordRaw } from "vue-router";

export const authenticationRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'register',
    component: RegistrationView,
    meta: { requiresAuth: false }
  }
]
