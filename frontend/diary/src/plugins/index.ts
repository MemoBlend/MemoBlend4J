import { router } from "@/router";
import type { App } from "vue";
import pinia from '../stores';
import vuetify from '../plugins/vuetify';

export function registerPlugins(app: App) {
  app
    .use(vuetify)
    .use(router)
    .use(pinia)
}
