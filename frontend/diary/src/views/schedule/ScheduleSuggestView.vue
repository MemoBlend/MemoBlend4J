<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import type { GetRecommendedScheduleResponse } from '@/generated/api-client';
import { getRecommendedSchedule } from '@/services/schedule/schedule-service';
import { useCustomErrorHandler } from '@/shared/error-handler/use-custom-error-handler';

const recommendedSchedule = ref<GetRecommendedScheduleResponse | null>(null);
const loading = ref(true);
const errorMessage = ref('');

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
    <v-card elevation="8" max-width="500" class="pa-6">
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
        <div
          v-else-if="recommendedSchedule?.recommendedSchedule"
          class="text-body-1 text-center my-6"
        >
          <v-icon color="success" class="mb-2" size="36"
            >mdi-lightbulb-on-outline</v-icon
          >
          <div>{{ recommendedSchedule.recommendedSchedule }}</div>
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
