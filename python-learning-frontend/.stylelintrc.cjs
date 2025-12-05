module.exports = {
  extends: [
    'stylelint-config-standard-scss',
    'stylelint-config-recommended-vue'
  ],
  rules: {
    // SCSS相关规则
    'scss/at-rule-no-unknown': true,
    'scss/dollar-variable-pattern': '^[a-z][a-zA-Z0-9]*$',
    'scss/selector-no-redundant-nesting-selector': true,
    
    // CSS相关规则
    'selector-class-pattern': '^[a-z][a-zA-Z0-9]*$',
    'color-hex-length': 'short',
    'color-named': 'never',
    'declaration-block-no-duplicate-properties': true,
    'declaration-no-important': true,
    'font-family-name-quotes': 'always-where-required',
    'font-weight-notation': 'numeric',
    'function-url-quotes': 'always',
    'length-zero-no-unit': true,
    'max-nesting-depth': 4,
    'no-descending-specificity': null,
    'number-leading-zero': 'always',
    'property-no-vendor-prefix': true,
    'selector-max-id': 0,
    'selector-no-vendor-prefix': true,
    'value-no-vendor-prefix': true,
    
    // Vue相关规则
    'selector-pseudo-class-no-unknown': [
      true,
      {
        ignorePseudoClasses: ['deep', 'global']
      }
    ]
  }
}