import { createVuetify } from "vuetify";
import { VCalendar, VDateInput } from "vuetify/labs/components";

export default createVuetify({
  components: {
    VDateInput,
    VCalendar
  },
  theme: {
    defaultTheme: 'dark',
  },
})
