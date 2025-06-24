<script setup lang="ts">
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import { storeToRefs } from 'pinia';
import { useAuthenticationStore } from './stores/authentication';
import { signOutAsync } from './services/authentication/authentication-service';

const showMenu = ref(false);
const authenticationStore = useAuthenticationStore();
const { name, isAuthenticated } = storeToRefs(authenticationStore);

const menuAction = () => {
  showMenu.value = !showMenu.value;
};

const darkTheme = ref(true);
const theme = useTheme();

const changeTheme = () => {
  theme.global.name.value = darkTheme.value ? 'dark' : 'light';
};

const signOut = async () => {
  await signOutAsync();
};
</script>

<template>
  <v-app>
    <v-app-bar rounded>
      <template v-slot:prepend>
        <v-app-bar-nav-icon @click="menuAction"></v-app-bar-nav-icon>
      </template>
      <v-app-bar-title bold>
        <RouterLink
          to="/"
          class="font-weight-bold text-decoration-none"
          :class="darkTheme ? 'text-white' : 'text-black'"
        >
          MemoBlend</RouterLink
        >
      </v-app-bar-title>
      <RouterLink
        v-if="!isAuthenticated"
        to="/login"
        class="text-decoration-none mr-4"
        :class="darkTheme ? 'text-white' : 'text-black'"
        >ログイン
      </RouterLink>
      <v-btn v-if="isAuthenticated" variant="text" class="me-3">
        <v-icon start icon="mdi-account"></v-icon>
        {{ name }}
      </v-btn>
      <RouterLink
        v-if="isAuthenticated"
        to="/login"
        @click="signOut"
        class="text-decoration-none mr-4"
        :class="darkTheme ? 'text-white' : 'text-black'"
      >
        ログアウト
      </RouterLink>
      <v-switch
        v-model="darkTheme"
        @update:model-value="changeTheme"
        :prepend-icon="darkTheme ? 'mdi-weather-night' : 'mdi-weather-sunny'"
        hide-details
        inset
        class="mr-auto"
      />
    </v-app-bar>

    <v-navigation-drawer v-model="showMenu">
      <v-list nav>
        <v-list-item>
          <v-list-item-title>
            <RouterLink
              to="/"
              class="text-decoration-none"
              :class="darkTheme ? 'text-white' : 'text-black'"
              >HOME
            </RouterLink>
          </v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>
            <RouterLink
              to="/schedule/suggest"
              class="text-decoration-none"
              :class="darkTheme ? 'text-white' : 'text-black'"
              >SCHEDULE
            </RouterLink>
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <RouterView />
    </v-main>
    <v-footer class="bg-grey-lighten-1" app> &copy; MemoBlend - 2025 </v-footer>
  </v-app>
</template>
