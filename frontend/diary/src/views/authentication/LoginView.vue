<script setup lang="ts">
import { signInAsync } from '@/services/authentication/authentication-service';
import { loginFormSchema } from '@/validation';
import { useForm } from 'vee-validate';
import { useRouter } from 'vue-router';

const router = useRouter();
const { errors, values, meta, defineField } = useForm({
  validationSchema: loginFormSchema,
});
const [email] = defineField('email');
const [password] = defineField('password');

/**
 * ログインします。
 */
const login = async () => {
  await signInAsync();
  router.push({ name: 'diaries' });
};

/**
 * バリデーションエラーがあるかどうかを返します。
 */
const isInValid = () => {
  return !meta.value.valid;
};
</script>
<template>
  <v-card class="mx-auto my-8" max-width="400">
    <v-card-item>
      <v-card-title> ログイン </v-card-title>

      <v-card-subtitle>
        メールアドレスとパスワードを入力してください
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <v-form>
        <v-text-field
          v-model="email"
          label="メールアドレス"
          :error-messages="errors.email"
        ></v-text-field>
        <v-text-field
          v-model="password"
          label="パスワード"
          type="password"
          :error-messages="errors.password"
        ></v-text-field>
        <v-btn block color="primary" @click="login" :disabled="isInValid()">
          ログイン
        </v-btn>
      </v-form>
    </v-card-text>
  </v-card>
</template>
