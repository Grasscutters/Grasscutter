import tailwind from "tailwindcss";
import autoprefixer from "autoprefixer";
import cssnanoPlugin from "cssnano";

import tailwindConfig from "./tailwind.config.js";
const mode = process.env.NODE_ENV;
const dev = mode === "development";

export default {
    plugins: (() => {
        // Create an empty array to store the plugins
        let plugins = [
            // Some plugins, like TailwindCSS/Nesting, need to run before Tailwind.
            tailwind(tailwindConfig),

            // But others, like autoprefixer, need to run after.
            autoprefixer()
        ];

        // Add the cssnano plugin if it's not in development mode
        !dev && plugins.push(
            cssnanoPlugin({
                preset: "default"
            })
        );

        return plugins;
    })()
}