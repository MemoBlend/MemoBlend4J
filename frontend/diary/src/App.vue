<script setup lang="ts">
import { ref } from 'vue';
import { useTheme } from 'vuetify';

const showMenu = ref(false);

const menuAction = () => {
  if (showMenu.value) {
    showMenu.value = false;
  } else {
    showMenu.value = true;
  }
}

const darkTheme = ref(true)
const theme = useTheme()

const changeTheme = () => {
  theme.global.name.value = darkTheme.value ? 'dark' : 'light';
}
</script>

<template>
  <v-app>
    <v-app-bar rounded>
      <template v-slot:prepend>
        <v-app-bar-nav-icon @click="menuAction"></v-app-bar-nav-icon>
      </template>
      <v-app-bar-title bold>
        <RouterLink to="/" class="font-weight-bold text-decoration-none"
          :class="darkTheme ? 'text-white' : 'text-black'">
          MemoBlend</RouterLink>
      </v-app-bar-title>
      <v-switch v-model="darkTheme" @update:model-value="changeTheme"
        :prepend-icon="darkTheme ? 'mdi-weather-night' : 'mdi-weather-sunny'" hide-details inset class="mr-auto" />
    </v-app-bar>

    <v-navigation-drawer v-model="showMenu">
      <v-list nav>
        <v-list-item>
          <v-list-item-title>
            <RouterLink to="/" class="text-decoration-none" :class="darkTheme ? 'text-white' : 'text-black'">HOME
            </RouterLink>
          </v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>
            <RouterLink to="#" class="text-decoration-none" :class="darkTheme ? 'text-white' : 'text-black'">ANALYSIS
            </RouterLink>
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <RouterView />
    </v-main>
    <v-footer class="bg-grey-lighten-1" app>
      &copy MemoBlend - 2025
    </v-footer>
  </v-app>
</template>
