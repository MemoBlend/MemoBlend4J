<script setup lang="ts">
import type { CalendarEvent } from '@/types';

const props = defineProps<{
  event: CalendarEvent;
}>();
</script>

<template>
  <v-chip :class="props.event.color" variant="flat" size="small">
    {{ props.event.title }}
  </v-chip>
</template>
