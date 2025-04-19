<script setup lang="ts">
import { EventList } from '@/components/molecules/EventList';
import type { CalendarEvent } from '@/types';
import { ref, computed } from 'vue';

const props = defineProps<{
  diaryData: CalendarEvent[],
  onEventClick: (id: number) => void
}>()

const currentDate = ref(new Date());
const today = ref(new Date());

const currentYear = computed(() => currentDate.value.getFullYear());
const currentMonth = computed(() => currentDate.value.getMonth());

const daysInMonth = computed(() => {
  const date = new Date(currentYear.value, currentMonth.value + 1, 0);
  return Array.from({ length: date.getDate() }, (_, i) => i + 1);
});

const blankDays = computed(() => Array.from({ length: new Date(currentYear.value, currentMonth.value, 1).getDay() }));

const trailingDays = computed(() => {
  const totalCells = blankDays.value.length + daysInMonth.value.length;
  return Array.from({ length: (7 - (totalCells % 7)) % 7 });
});

const weekDays = ['日', '月', '火', '水', '木', '金', '土'];

const getDateString = (day: number) =>
  new Date(currentYear.value, currentMonth.value, day).toISOString().slice(0, 10);

function changeMonth(offset: number) {
  currentDate.value = new Date(currentYear.value, currentMonth.value + offset, 1);
}

function goToday() {
  currentDate.value = new Date();
}

const eventMap = computed(() => {
  const map: Record<string, CalendarEvent[]> = {};
  props.diaryData.forEach(event => {
    const dateKey = new Date(event.date).toISOString().slice(0, 10);
    (map[dateKey] ||= []).push(event);
  });
  return map;
});

const hasDiaryEntry = (day: number) => !!eventMap.value[getDateString(day)]?.length;

const isToday = (day: number) =>
  currentYear.value === today.value.getFullYear() &&
  currentMonth.value === today.value.getMonth() &&
  day === today.value.getDate();

const getDiaryEvents = (day: number) => eventMap.value[getDateString(day)] || [];

const weeks = computed(() => {
  const cells = [...blankDays.value, ...daysInMonth.value, ...trailingDays.value];
  return Array.from({ length: Math.ceil(cells.length / 7) }, (_, i) => cells.slice(i * 7, i * 7 + 7));
});
</script>

<template>
  <v-container>
    <v-row>
      <v-col class="text-left">
        <v-btn @click="changeMonth(-1)">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-col>
      <v-col class="text-center">
        <span>{{ currentYear }}年 {{ currentMonth + 1 }}月</span>
      </v-col>
      <v-col class="text-right">
        <v-btn @click="changeMonth(1)">
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <div class="mt-3 mb-3">
      <v-btn @click="goToday">TODAY</v-btn>
    </div>

    <div class="ma-3">
      <v-row>
        <v-col v-for="day in weekDays" class="text-center font-weight-bold">
          {{ day }}
        </v-col>
      </v-row>

      <v-row v-for="week in weeks">
        <v-col v-for="cell in week" :class="['border', { 'today-cell': typeof cell === 'number' && isToday(cell) }]">
          <template v-if="cell !== undefined">
            <span>{{ cell }}</span>
            <EventList v-if="typeof cell === 'number' && hasDiaryEntry(cell)" :event-list="getDiaryEvents(cell)"
              :onEventClick="onEventClick" />
          </template>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<style scoped>
.today-cell {
  background-color: rgb(100, 100, 100, 0.2);
}
</style>
