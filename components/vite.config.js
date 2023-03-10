import federation from "@originjs/vite-plugin-federation";

export default {
  build: {
    target: "esnext",
  },
  plugins: [
    federation({
      name: "iot-components",
      filename: "iot-components.js",
      exposes: {
        "./hello": "./main.js",
      },
      shared: {
        "@vaadin/vaadin-lumo-styles": {},
        "@vaadin/button": { generate: false },
        "@vaadin/charts": {},
        "@vaadin/notification": {},
        "@vaadin/text-field": {},
        "j-elements": {},

        "@polymer/polymer": {},
        "lit-html": { version: "2.6.1" },
        lit: { version: "2.6.1" },
        "lit-element": { version: "2.6.1" },
      },
    }),
  ],
};
