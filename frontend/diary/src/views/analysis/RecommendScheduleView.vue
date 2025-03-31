<script setup lang="ts">
import type { GetRecommendedSchedulesResponse } from '@/generated/api-client';
import { getRecommendedSchedules } from '@/services/diary/diary-service';
import { ref } from 'vue';

const recommendedSchedule = ref<GetRecommendedSchedulesResponse>();
const scheduleColors = ref<string[]>([]);

const timelineColor = [
  'purple-lighten-2',
  'red-lighten-2',
  'blue-lighten-2',
  'green-lighten-2',
  'orange-lighten-2',
  'pink-lighten-2',
];

const fetchRecommendedSchedules = async () => {
  try {
    recommendedSchedule.value = await getRecommendedSchedules(1);
    if (recommendedSchedule.value?.schedules) {
      scheduleColors.value = recommendedSchedule.value.schedules.map(() =>
        timelineColor[Math.floor(Math.random() * timelineColor.length)]
      );
    }
  } catch (error) {
    console.error('Error fetching recommended schedules:', error);
  }
};
</script>

<template>
  <div v-if="!recommendedSchedule" class="d-flex justify-center align-center" style="min-height: 100vh;">
    <div>おすすめのスケジュールを取得します。</div>
    <v-btn @click="fetchRecommendedSchedules">取得</v-btn>
  </div>

  <v-fade-transition>
    <v-timeline v-if="recommendedSchedule">
      <v-timeline-item v-for="(schedule, index) in recommendedSchedule?.schedules" :key="index"
        :dot-color="scheduleColors[index]" fill-dot>
        <v-card class="mx-auto" max-width="400">
          <v-card-title :class="`bg-${scheduleColors[index]}`">
            <v-icon class="me-4" icon="mdi-magnify" size="large"></v-icon>
            {{ schedule.title }}
          </v-card-title>
          <v-card-text class="mt-5">
            {{ schedule.description }}
          </v-card-text>
          <v-card-subtitle>
            <v-icon class="me-4" icon="mdi-map-marker" size="large" color="red"></v-icon>
            {{ schedule.location }}
          </v-card-subtitle>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  </v-fade-transition>
</template>
