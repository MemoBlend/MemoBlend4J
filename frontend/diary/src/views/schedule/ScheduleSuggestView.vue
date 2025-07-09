<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue';
import type { GetRecommendedScheduleResponse } from '@/generated/api-client';
import { getRecommendedSchedule } from '@/services/schedule/schedule-service';
import { useCustomErrorHandler } from '@/shared/error-handler/use-custom-error-handler';

interface ScheduleItem {
  point_name: string;
  description: string;
  start_time: string;
  end_time: string;
  order: string;
}

const recommendedSchedule = ref<GetRecommendedScheduleResponse | null>(null);
const loading = ref(true);
const errorMessage = ref('');

const scheduleItems = computed(() => {
  if (!recommendedSchedule.value?.recommendedSchedule) return [];

  const schedule = recommendedSchedule.value.recommendedSchedule;
  const items = Object.keys(schedule)
    .sort((a, b) => parseInt(a) - parseInt(b))
    .map((key) => ({
      ...schedule[key],
      order: key,
    }));
  return items;
});

const formatTime = (dateString: string) => {
  const date = new Date(dateString);
  const formatted = date.toLocaleTimeString('ja-JP', {
    hour: '2-digit',
    minute: '2-digit',
  });
  return formatted;
};

onMounted(async () => {
  const customErrorHandler = useCustomErrorHandler();
  try {
    recommendedSchedule.value = await getRecommendedSchedule();
  } catch (error) {
    customErrorHandler.handle(error, () => {
      errorMessage.value = 'スケジュールの取得に失敗しました。';
    });
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <v-container class="fill-height d-flex justify-center align-center">
    <v-card elevation="8" max-width="800" class="pa-6">
      <v-card-title class="text-h5 font-weight-bold mb-4">
        <v-icon color="primary" class="mr-2">mdi-calendar-star</v-icon>
        おすすめスケジュール
      </v-card-title>
      <v-card-text>
        <v-progress-circular
          v-if="loading"
          indeterminate
          color="primary"
          size="48"
          class="mx-auto d-block mb-4"
        />
        <v-alert
          v-else-if="errorMessage"
          type="error"
          class="mb-4"
          border="start"
          border-color="error"
        >
          <v-icon start color="error">mdi-alert-circle</v-icon>
          {{ errorMessage }}
        </v-alert>
        <div v-else-if="scheduleItems.length > 0" class="my-6">
          <v-card
            v-for="item in scheduleItems"
            :key="item.order"
            class="mb-4"
            elevation="2"
          >
            <v-card-title class="text-h6 pb-2">
              <v-icon color="primary" class="mr-2">mdi-map-marker</v-icon>
              {{ item.point_name }}
            </v-card-title>
            <v-card-text class="pt-0">
              <div class="mb-2">
                <v-icon size="small" color="primary" class="mr-1"
                  >mdi-clock-outline</v-icon
                >
                <strong
                  >{{ formatTime(item.start_time) }} -
                  {{ formatTime(item.end_time) }}</strong
                >
              </div>
              <div class="text-body-2">
                {{ item.description }}
              </div>
            </v-card-text>
          </v-card>
        </div>
        <v-alert
          v-else
          type="info"
          class="mb-4"
          border="start"
          border-color="info"
        >
          <v-icon start color="info">mdi-information</v-icon>
          おすすめスケジュールがありません。
        </v-alert>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn color="primary" variant="tonal" @click="$router.back()">
          <v-icon start>mdi-arrow-left</v-icon>
          戻る
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<style scoped>
.fill-height {
  min-height: 80vh;
}
</style>
