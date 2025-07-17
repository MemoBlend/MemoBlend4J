import type { TypedSchema } from 'vee-validate';
import { toTypedSchema } from '@vee-validate/yup';
import * as yup from 'yup';

export const loginFormSchema: TypedSchema = toTypedSchema(
  yup.object({
    authId: yup.string().required('認証IDは必須です。'),
    password: yup.string().required('パスワードは必須です。'),
  }),
);
