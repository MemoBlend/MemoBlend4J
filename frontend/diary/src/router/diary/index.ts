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
  },
  {
    path: '/detail/:id',
    name: 'detail',
    component: DiaryDetailView,
  },
  {
    path: '/detail/:id/edit',
    name: 'edit',
    component: DiaryEditView,
  },
  {
    path: '/create',
    name: 'create',
    component: DiaryCreateView,
  },
]
