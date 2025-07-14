<script setup lang="ts">
import { signInAsync } from '@/services/authentication/authentication-service';
import { loginFormSchema } from '@/validation';
import { useForm } from 'vee-validate';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const isLoading = ref(false);
const errorMessage = ref('');

const { errors, meta, defineField } = useForm({
  validationSchema: loginFormSchema,
});
const [authId] = defineField('authId');
const [password] = defineField('password');

/**
 * ログインします。
 */
const login = async () => {
  if (!authId.value || !password.value) {
    errorMessage.value = '認証IDとパスワードを入力してください。';
    return;
  }

  isLoading.value = true;
  errorMessage.value = '';

  try {
    await signInAsync(authId.value, password.value);
    router.push({ name: 'diaries' });
  } catch (error) {
    console.error('ログインエラー:', error);
    errorMessage.value = 'ログインに失敗しました。認証IDとパスワードを確認してください。';
  } finally {
    isLoading.value = false;
  }
};

/**
 * バリデーションエラーがあるかどうかを返します。
 */
const isInValid = () => {
  return !meta.value.valid || isLoading.value;
};
</script>
<template>
  <v-card class="mx-auto my-8" max-width="400">
    <v-card-item>
      <v-card-title> ログイン </v-card-title>

      <v-card-subtitle>
        認証IDとパスワードを入力してください
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <!-- エラーメッセージ表示 -->
      <v-alert v-if="errorMessage" type="error" class="mb-4" :text="errorMessage"></v-alert>

      <v-form>
        <v-text-field v-model="authId" label="認証ID" :error-messages="errors.authId"
          :disabled="isLoading"></v-text-field>
        <v-text-field v-model="password" label="パスワード" type="password" :error-messages="errors.password"
          :disabled="isLoading"></v-text-field>
        <v-btn block color="primary" @click="login" :disabled="isInValid()" :loading="isLoading">
          ログイン
        </v-btn>
      </v-form>
    </v-card-text>
  </v-card>
</template>
