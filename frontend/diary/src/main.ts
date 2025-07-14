import 'vuetify/styles';
import '@mdi/font/css/materialdesignicons.css';

import { createApp } from 'vue';
import App from './App.vue';
import { globalErrorHandler } from './shared/error-handler/global-error-handler';
import { createCustomErrorHandler } from './shared/error-handler/custom-error-handler';
import { registerPlugins } from './plugins';
import { authenticationGuard } from './shared/authentication/authentication-guard';
import { restoreSessionAsync } from './services/authentication/authentication-service';
import { router } from './router';

const app = createApp(App);
registerPlugins(app);
app.use(globalErrorHandler);
app.use(createCustomErrorHandler());
authenticationGuard(router);

// アプリケーション起動時にセッションを復元
restoreSessionAsync().catch((error) => {
  console.warn('セッション復元に失敗しました:', error.message);
});

app.mount('#app');
