import federation from '@originjs/vite-plugin-federation';

export default {
  base: '',
  build: {
    target: 'esnext',
  },
  plugins: [
    federation({
      name: 'iot-components',
      remotes: {
        'room-info': '/room-info/VAADIN/build/roominfo.js',
      },
      filename: 'iot-components.js',
      exposes: {
        './all.js': './main.js',
      },
      shared: {
        '@vaadin/vaadin-lumo-styles': {},
        '@vaadin/button': {},
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
};
