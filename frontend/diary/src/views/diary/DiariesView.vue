<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { GetDiariesResponse } from '@/generated/api-client';
import { getDiaries } from '@/services/diary/diary-service';
import { useCustomErrorHandler } from '@/shared/error-handler/use-custom-error-handler';
import { useRouter } from 'vue-router';
import type { CalendarEvent } from '@/types';
import { EventCalendar } from '@/components/organisms/EventCalendar';
import { LoadingSpinnerOverlay } from '@/components/organisms/LoadingSpinnerOverlay';
import { useDiaryStore } from '@/stores/diary';

const showLoading = ref(true);
const customErrorHandler = useCustomErrorHandler();
const diariesResponse = ref<GetDiariesResponse>({
  diaries: [],
});
const events = ref<CalendarEvent[]>([]);
const router = useRouter();
const diaryStore = useDiaryStore();

/**
 * 日記詳細に遷移します。
 * @param id 日記の ID 。
 */
const goToDiaryDetail = (id: number) => {
  router.push({ name: 'detail', params: { id: id } });
};

/**
 * 日記作成画面に遷移します。
 */
const goToCreateDiary = () => {
  router.push({ name: 'create' });
};

onMounted(async () => {
  showLoading.value = true;
  try {
    diariesResponse.value = await getDiaries();
  } catch (error) {
    customErrorHandler.handle(error, () => {
      router.push({ name: 'error' });
    });
  } finally {
    showLoading.value = false;
  }

  const eventsList: CalendarEvent[] = [];
  if (diariesResponse.value.diaries) {
    for (const diary of diariesResponse.value.diaries) {
      const event = {
        id: diary.id,
        title: diary.title,
        date: new Date(diary.createdDate),
        color: 'red',
        allDay: false,
      };
      eventsList.push(event);
    }
  }
  events.value = eventsList;
});
</script>

<template>
  <LoadingSpinnerOverlay :isLoading="showLoading" />
  <div v-if="!showLoading">
    <EventCalendar :diaryData="events" :onEventClick="goToDiaryDetail" />
  </div>
  <v-btn
    style="z-index: 2"
    icon="$plus"
    class="position-fixed bottom-0 right-0 ma-4"
    fab
    color="blue-grey"
    @click="goToCreateDiary"
  ></v-btn>
</template>
