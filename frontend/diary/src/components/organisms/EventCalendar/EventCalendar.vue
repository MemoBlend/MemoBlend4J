<script setup lang="ts">
import { EventList } from '@/components/molecules/EventList';
import { useDiaryStore } from '@/stores/diary';
import { getDiariesByYearMonth } from '@/services/diary/diary-service';
import type { CalendarEvent } from '@/types';
import { ref, computed } from 'vue';

const props = defineProps<{
  diaryData: CalendarEvent[];
  onEventClick: (id: number) => void;
}>();

const diaryStore = useDiaryStore();

const currentDate = ref(new Date());
const today = ref(new Date());

const currentYear = computed(() => currentDate.value.getFullYear());
const currentMonth = computed(() => currentDate.value.getMonth());

const daysInMonth = computed(() => {
  const date = new Date(currentYear.value, currentMonth.value + 1, 0);
  return Array.from({ length: date.getDate() }, (_, i) => i + 1);
});

const blankDays = computed(() =>
  Array.from({
    length: new Date(currentYear.value, currentMonth.value, 1).getDay(),
  }),
);

const trailingDays = computed(() => {
  const totalCells = blankDays.value.length + daysInMonth.value.length;
  return Array.from({ length: (7 - (totalCells % 7)) % 7 });
});

const weekDays = ['日', '月', '火', '水', '木', '金', '土'];

const getDateString = (day: number) =>
  new Date(currentYear.value, currentMonth.value, day)
    .toISOString()
    .slice(0, 10);

const events = ref<CalendarEvent[]>(props.diaryData);
const loading = ref(false);

async function fetchMonthEvents(year: number, month: number) {
  loading.value = true;
  try {
    const res = await getDiariesByYearMonth(year, month + 1); // month: 1-12
    events.value = (res.diaries || []).map((diary) => ({
      id: diary.id,
      title: diary.title,
      date: new Date(diary.createdDate),
      color: 'red',
    }));
  } finally {
    loading.value = false;
  }
}

function nextMonth() {
  currentDate.value = new Date(currentYear.value, currentMonth.value + 1, 1);
  diaryStore.setDiaryYear(currentDate.value.getFullYear());
  diaryStore.setDiaryMonth(currentDate.value.getMonth() + 1);
  fetchMonthEvents(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth(),
  );
}

function prevMonth() {
  currentDate.value = new Date(currentYear.value, currentMonth.value - 1, 1);
  diaryStore.setDiaryYear(currentDate.value.getFullYear());
  diaryStore.setDiaryMonth(currentDate.value.getMonth() + 1);
  fetchMonthEvents(
    currentDate.value.getFullYear(),
    currentDate.value.getMonth(),
  );
}

function goToday() {
  currentDate.value = new Date();
}

const eventMap = computed(() => {
  const map: Record<string, CalendarEvent[]> = {};
  events.value.forEach((event) => {
    const dateKey = new Date(event.date).toISOString().slice(0, 10);
    (map[dateKey] ||= []).push(event);
  });
  return map;
});

const hasDiaryEntry = (day: number) =>
  !!eventMap.value[getDateString(day)]?.length;

const isToday = (day: number) =>
  currentYear.value === today.value.getFullYear() &&
  currentMonth.value === today.value.getMonth() &&
  day === today.value.getDate();

const getDiaryEvents = (day: number) =>
  eventMap.value[getDateString(day)] || [];

const weeks = computed(() => {
  const cells = [
    ...blankDays.value,
    ...daysInMonth.value,
    ...trailingDays.value,
  ];
  return Array.from({ length: Math.ceil(cells.length / 7) }, (_, i) =>
    cells.slice(i * 7, i * 7 + 7),
  );
});
</script>

<template>
  <v-container>
    <v-row>
      <v-col class="text-left">
        <v-btn @click="prevMonth">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-col>
      <v-col class="text-center">
        <span>{{ currentYear }}年 {{ currentMonth + 1 }}月</span>
      </v-col>
      <v-col class="text-right">
        <v-btn @click="nextMonth">
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <div class="mt-3 mb-3">
      <v-btn @click="goToday">TODAY</v-btn>
    </div>

    <div class="ma-3">
      <v-row>
        <v-col
          v-for="(day, i) in weekDays"
          :key="i"
          class="text-center font-weight-bold"
        >
          {{ day }}
        </v-col>
      </v-row>

      <v-row v-for="(week, wIdx) in weeks" :key="wIdx">
        <v-col
          v-for="(cell, cIdx) in week"
          :key="cIdx"
          :class="[
            'border',
            { 'today-cell': typeof cell === 'number' && isToday(cell) },
          ]"
        >
          <template v-if="cell !== undefined">
            <span>{{ cell }}</span>
            <EventList
              v-if="typeof cell === 'number' && hasDiaryEntry(cell)"
              :event-list="getDiaryEvents(cell)"
              :onEventClick="onEventClick"
            />
          </template>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<style scoped>
.today-cell {
  background-color: rgb(100 100 100 / 20%);
}
</style>
