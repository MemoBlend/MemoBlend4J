import 'vuetify/styles';
import '@mdi/font/css/materialdesignicons.css'

import { createApp } from 'vue'
import App from './App.vue'
import { globalErrorHandler } from './shared/error-handler/global-error-handler';
import { createCustomErrorHandler } from './shared/error-handler/custom-error-handler';
import { registerPlugins } from './plugins';
import { authenticationGuard } from './shared/authentication/authentication-guard';
import { router } from './router';

const app = createApp(App);
registerPlugins(app);
app.use(globalErrorHandler);
app.use(createCustomErrorHandler());
authenticationGuard(router);
app.mount('#app')
