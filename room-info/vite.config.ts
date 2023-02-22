import federation from '@originjs/vite-plugin-federation';

import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
  build: {
    target: 'esnext',
  },
  plugins: [
    federation({
      name: 'iot-roominfo',
      filename: 'roominfo.js',
      exposes: {
        './room-info': './frontend/room-info.ts',
      },
      shared: {
        '@vaadin/vaadin-lumo-styles': {},
        '@vaadin/button': { generate: false },
        '@vaadin/charts': {},
        '@vaadin/notification': {},
        '@vaadin/text-field': {},
        'j-elements': {},

        '@polymer/polymer': {},
        'lit-html': { version: '2.6.1' },
        lit: { version: '2.6.1' },
        'lit-element': { version: '2.6.1' },
      },
    }),
  ],
});

export default overrideVaadinConfig(customConfig);
