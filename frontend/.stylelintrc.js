export default {
  plugins: ['stylelint-prettier'],
  extends: [
    'stylelint-config-standard',
    'stylelint-config-recommended-vue',
    'stylelint-prettier/recommended',
  ],
  rules: {
    'prettier/prettier': true,
    'at-rule-no-unknown': [true, { ignoreAtRules: ['define-mixin'] }],
  },
  ignoreFiles: ['dist/**/*'],
};
