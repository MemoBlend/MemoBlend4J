<script setup lang="ts">
import { Event } from '@/components/atoms/Event';
import type { CalendarEvent } from '@/types';

const props = defineProps<{
  eventList: CalendarEvent[];
  onEventClick: (id: number) => void;
}>();
</script>

<template>
  <div>
    <div
      v-for="event in props.eventList"
      :key="event.id"
      @click="() => props.onEventClick(event.id)"
    >
      <Event :event="event" />
    </div>
  </div>
</template>
