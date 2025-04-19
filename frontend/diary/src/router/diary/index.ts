import DiariesView from '@/views/diary/DiariesView.vue';
import DiaryCreateView from '@/views/diary/DiaryCreateView.vue'
import DiaryDetailView from '@/views/diary/DiaryDetailView.vue'
import DiaryEditView from '@/views/diary/DiaryEditView.vue'
import type { RouteRecordRaw } from "vue-router";

export const diaryRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'diaries',
    component: DiariesView,
    meta: {
      requiresAuth: true,
    }
  },
  {
    path: '/detail/:id',
    name: 'detail',
    component: DiaryDetailView,
    meta: {
      requiresAuth: true,
    }
  },
  {
    path: '/detail/:id/edit',
    name: 'edit',
    component: DiaryEditView,
    meta: {
      requiresAuth: true,
    }
  },
  {
    path: '/create',
    name: 'create',
    component: DiaryCreateView,
    meta: {
      requiresAuth: true,
    }
  },
]
