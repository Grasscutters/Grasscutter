// noinspection JSUnusedGlobalSymbols

import { defineConfig } from "vite";

import react from "@vitejs/plugin-react-swc";
import viteSvgr from "vite-plugin-svgr";
import tsconfigPaths from "vite-tsconfig-paths";

import postcss from "./cfg/postcss.config.js";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [ react(), tsconfigPaths(), viteSvgr() ],
    css: { postcss }
});
