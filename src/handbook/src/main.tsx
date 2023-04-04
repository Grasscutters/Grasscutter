import React from "react";
import { createRoot } from "react-dom/client";

import App from "@components/App";

// Render the application.
createRoot(document.getElementById(
    "root") as HTMLElement).render(
        <React.StrictMode>
            <App />
        </React.StrictMode>
);
