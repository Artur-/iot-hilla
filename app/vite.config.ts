import federation from '@originjs/vite-plugin-federation';

import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
  build: {
    target: 'esnext',
  },
  plugins: [
    federation({
      name: 'iot-app',
      remotes: {
        components: '/_apps/components/iot-components.js',
        roominfo: '/_apps/room-info/VAADIN/build/room-info.js',
      },
      shared: {
        // '@vaadin/vaadin-lumo-styles': {},
        // '@vaadin/button': {},
        // '@vaadin/notification': {},
        'lit-html': { version: '2.6.1' },
        lit: { version: '2.6.1' },
        'lit-element': { version: '2.6.1' },
        // '@polymer/polymer': {},
        // 'my-component': { version: '1.0.0', generate: false },
      },
      //  shared: ["@vaadin/button"],
      //      shared: ["lit-html"],
      // shared: ["lit", "lit-html", "lit-element"],
    }),
  ],
});

export default overrideVaadinConfig(customConfig);
