<script setup lang="ts">
import { LoadingSpinnerOverlay } from '@/components/organisms/LoadingSpinnerOverlay';
import type { GetDiaryResponse } from '@/generated/api-client';
import { deleteDiary, getDiary } from '@/services/diary/diary-service';
import { useCustomErrorHandler } from '@/shared/error-handler/use-custom-error-handler';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const customErrorHandler = useCustomErrorHandler();
const route = useRoute();
const id = Number(route.params.id);
const showLoading = ref(true);

/**
 * 日記の詳細を保持するオブジェクトです。
 */
const diary = ref<GetDiaryResponse>({
  content: '',
  createdDate: '',
  id: id,
  title: '',
  userId: 0,
})

const router = useRouter();

/**
 * 削除モーダルの表示状態を保持するオブジェクトです。
 */
const showDeleteModal = ref(false);

/**
 * 削除モーダルを閉じます。
 */
const closeDeleteModal = () => {
  showDeleteModal.value = false;
}

/**
 * 日記を削除します。
 * 削除した後に日記の一覧画面に遷移します。
 */
const deleteDiaryAsync = async () => {
  showLoading.value = true;
  try {
    await deleteDiary(id);
  } catch (error) {
    customErrorHandler.handle(error, () => {
      router.push({ name: 'error' });
    });
  }
  router.push({ name: 'diaries' });
}

/**
 * 編集画面に遷移します。
 */
const goToEditDiary = () => {
  router.push({ name: 'edit' });
}

onMounted(async () => {
  try {
    diary.value = await getDiary(id);
  } catch (error) {
    customErrorHandler.handle(error, () => {
      router.push({ name: 'error' });
    });
  } finally {
    showLoading.value = false;
  }
});
</script>

<template>
  <LoadingSpinnerOverlay :isLoading="showLoading" />
  <v-container v-if="!showLoading">
    <h1>{{ diary.title }}</h1>
    <p>{{ diary.createdDate }}</p>
    <p>{{ diary.content }}</p>
    <div class="mt-4">
      <v-btn class="mr-4" @click="goToEditDiary">編集</v-btn>
      <v-dialog v-model="showDeleteModal" max-width="500">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn v-bind="activatorProps" text="削除"></v-btn>
        </template>
        <template v-slot:default>
          <v-card title="削除確認">
            <v-card-text>
              削除してもよろしいですか？
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text="はい" @click="deleteDiaryAsync"></v-btn>
              <v-btn text="いいえ" @click="closeDeleteModal"></v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </div>
  </v-container>
</template>
