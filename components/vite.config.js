import { resolve } from "path";
import { defineConfig } from "vite";

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
        ".": "./main.js",
      },
      shared: ["lit", "lit-html", "lit-element"],
    }),
  ],
};
