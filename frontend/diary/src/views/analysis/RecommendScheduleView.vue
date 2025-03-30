<script setup lang="ts">
import type { GetRecommendedSchedulesResponse } from '@/generated/api-client';
import { getRecommendedSchedules } from '@/services/diary/diary-service';
import { ref } from 'vue';

const recommendedSchedule = ref<GetRecommendedSchedulesResponse>();

// 時間文字列をJST時間に変換する関数を修正
const formatTimeString = (timeString: string): string => {
  if (!timeString) return '';
  const date = new Date(timeString);
  // UTC時間をそのまま使用（JSTへの変換を行わない）
  return `${date.getUTCHours().toString().padStart(2, '0')}:${date.getUTCMinutes().toString().padStart(2, '0')}`;
};

// 時間の長さを計算する関数を改善
const calculateDuration = (startTime: string, endTime: string): number => {
  try {
    const start = new Date(startTime);
    const end = new Date(endTime);
    // UTCのままで差分を計算（タイムゾーンの影響を受けない）
    const diffMinutes = (end.getTime() - start.getTime()) / (1000 * 60);
    return Math.abs(diffMinutes); // 負の値を防ぐ
  } catch {
    return 0;
  }
};

// 時間の長さを表示用にフォーマット
const formatDuration = (minutes: number): string => {
  if (!minutes || minutes < 0) return '0分';

  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return hours > 0 ? `${hours}時間${mins}分` : `${mins}分`;
};

// 時間の長さに基づいて高さを計算する関数を修正
const calculateHeight = (startTime: string, endTime: string): number => {
  const duration = calculateDuration(startTime, endTime);
  // 1分あたり4pxで計算（最小高さ80px）
  return Math.max(duration * 4, 80);
};

const fetchRecommend = async () => {
  try {
    const response = await getRecommendedSchedules(1);
    recommendedSchedule.value = response;
  } catch (error) {
    console.error('Error fetching recommended schedules:', error);
  }
};
</script>

<template>
  <v-container class="fill-height" v-if="recommendedSchedule === undefined">
    <v-row justify="center" class="text-center">
      <v-btn @click="fetchRecommend">おすすめスケジュールを取得</v-btn>
    </v-row>
  </v-container>
  <v-container class="fill-height" v-if="recommendedSchedule !== undefined">
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="text-h5 text-center">
            おすすめスケジュール
          </v-card-title>
          <v-card-text>
            <v-timeline v-if="recommendedSchedule?.schedules" side="end" line-thickness="2" density="comfortable">
              <v-timeline-item v-for="(schedule, index) in recommendedSchedule.schedules" :key="index"
                :color="index % 2 === 0 ? 'primary' : 'secondary'" size="large" :style="{
                  minHeight: `${calculateHeight(schedule.startTime, schedule.endTime)}px`,
                  marginBottom: '24px',
                  position: 'relative',
                  padding: '16px',
                  background: index % 2 === 0 ? '#F5F5F5' : '#FFFFFF',
                  borderRadius: '12px',
                  boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                  borderLeft: `4px solid ${index % 2 === 0 ? '#2196F3' : '#673AB7'}`,
                }">
                <template v-slot:icon>
                  <v-icon icon="mdi-clock-outline" color="grey darken-1"></v-icon>
                </template>
                <div>
                  <div class="text-h6 font-weight-bold">{{ schedule.title || '予定なし' }}</div>
                  <div class="text-caption text-grey">
                    {{ formatTimeString(schedule.startTime) }} - {{ formatTimeString(schedule.endTime) }}
                  </div>
                  <div v-if="schedule.location" class="text-caption mt-1 d-flex align-center" :style="{
                    color: '#FF5722',
                    fontWeight: 'bold',
                  }">
                    <v-icon icon="mdi-map-marker" color="#FF5722" class="mr-1"></v-icon>
                    場所: {{ schedule.location }}
                  </div>
                  <v-chip class="mt-2" size="small" :color="index % 2 === 0 ? 'primary' : 'secondary'"
                    variant="outlined" :style="{
                      fontWeight: 'bold',
                      backgroundColor: index % 2 === 0 ? '#F0F0F0' : '#F7F7F7',
                      color: '#424242',
                    }">
                    {{ formatDuration(calculateDuration(schedule.startTime, schedule.endTime)) }}
                  </v-chip>
                </div>
              </v-timeline-item>
            </v-timeline>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
