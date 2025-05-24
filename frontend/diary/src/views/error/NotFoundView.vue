<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
const goHome = () => router.push({ name: 'diaries' });
</script>

<template>
  <v-container class="fill-height d-flex align-center justify-center">
    <v-row justify="center">
      <v-col cols="12" md="8" class="text-center">
        <v-icon size="96" color="grey">mdi-alert-circle-outline</v-icon>
        <h1 class="display-2 font-weight-bold mb-2">404 Not Found</h1>
        <p class="mb-6">お探しのページは見つかりませんでした。</p>
        <v-btn color="primary" @click="goHome" large>ホームに戻る</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>
