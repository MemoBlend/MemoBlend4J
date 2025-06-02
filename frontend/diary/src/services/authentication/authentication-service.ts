import { useAuthenticationStore } from '@/stores/authentication';

export async function signInAsync() {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.signInAsync();
}

export async function signOutAsync() {
  const authenticationStore = useAuthenticationStore();
  await authenticationStore.signOutAsync();
}
