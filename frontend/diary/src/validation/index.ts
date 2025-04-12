import type { TypedSchema } from "vee-validate";
import { toTypedSchema } from '@vee-validate/yup';
import * as yup from 'yup';

export const loginFormSchema: TypedSchema = toTypedSchema(
  yup.object({
    email: yup
      .string()
      .required('メールアドレスは必須です。')
      .email('メールアドレスの形式で入力してください。'),
    password: yup
      .string()
      .required('パスワードは必須です。')
      .min(8, 'パスワードは8文字以上で入力してください。')
      .matches(/[A-Z]/, '大文字を1文字以上含める必要があります。')
      .matches(/[a-z]/, '小文字を1文字以上含める必要があります。')
      .matches(/[0-9]/, '数字を1文字以上含める必要があります。')
      .matches(/[!@#$%^&*(),.?":{}|<>]/, '特殊文字を1文字以上含める必要があります。')
  })
);
