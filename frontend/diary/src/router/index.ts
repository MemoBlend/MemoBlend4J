import { createRouter, createWebHistory } from 'vue-router';
import { diaryRoutes } from './diary';
import { errorRoutes } from './error';
import { authenticationRoutes } from './authentication';

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...diaryRoutes, ...errorRoutes, ...authenticationRoutes],
});
