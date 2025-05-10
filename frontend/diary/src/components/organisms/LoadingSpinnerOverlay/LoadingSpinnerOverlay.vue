<script setup lang="ts">
const props = defineProps<{
  isLoading: boolean;
}>();
</script>
<template>
  <div
    v-if="props.isLoading"
    class="d-flex align-center justify-center fill-height bg-blue-grey-lighten-4"
  >
    <v-progress-circular :size="50" indeterminate />
  </div>
</template>
