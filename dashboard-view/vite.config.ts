import federation from '@originjs/vite-plugin-federation';

import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
  build: {
    target: 'esnext',
  },
  plugins: [
    federation({
      name: 'dashboard-view',
      filename: 'dashboard-view.js',
      exposes: {
        './dashboard-view': './frontend/dashboard-view.ts',
      },
      shared: {
        '@vaadin/vaadin-lumo-styles': { import: false, generate: false },
        '@vaadin/button': { import: false, generate: false },
        '@vaadin/charts': { import: false, generate: false },
        '@vaadin/notification': { import: false, generate: false },
        '@vaadin/text-field': { import: false, generate: false },
        'j-elements': { import: false, generate: false },

        '@polymer/polymer': { generate: false, import: false },
        'lit-html': { import: false, generate: false },
        lit: { import: false, generate: false },
        'lit-element': { import: false, generate: false },
      },
    }),
  ],
});

export default overrideVaadinConfig(customConfig);
