// noinspection JSUnusedGlobalSymbols

import { defineConfig } from "vite";

import react from "@vitejs/plugin-react-swc";
import tsconfigPaths from "vite-tsconfig-paths";

import viteSvgr from "vite-plugin-svgr";
import { viteSingleFile } from "vite-plugin-singlefile";

import postcss from "./cfg/postcss.config.js";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [ react(), tsconfigPaths(), viteSvgr(), viteSingleFile() ],
    css: { postcss }
});
