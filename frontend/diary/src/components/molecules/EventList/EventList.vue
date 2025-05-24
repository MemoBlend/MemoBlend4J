<script setup lang="ts">
import { EventChip } from '@/components/atoms/EventChip';
import type { CalendarEvent } from '@/types';

const props = defineProps<{
  eventList: CalendarEvent[];
  onEventClick: (id: number) => void;
}>();
</script>

<template>
  <div>
    <EventChip
      v-for="event in props.eventList"
      :key="event.id"
      :event="event"
      @click="props.onEventClick(event.id)"
    />
  </div>
</template>
