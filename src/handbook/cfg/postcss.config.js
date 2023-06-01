import tailwind from "tailwindcss";
import autoprefixer from "autoprefixer";
import cssnanoPlugin from "cssnano";

import tailwindConfig from "./tailwind.config.js";
const mode = process.env.NODE_ENV;
const dev = mode === "development";

export default {
    plugins: (() => {
        let plugins = [
            // Some plugins, like TailwindCSS/Nesting, need to run before Tailwind.
            tailwind(tailwindConfig),

            // But others, like autoprefixer, need to run after.
            autoprefixer()
        ];

        !dev && cssnanoPlugin({
            preset: "default"
        });

        return plugins;
    })()
}
