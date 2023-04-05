import React from "react";
import { createRoot } from "react-dom/client";

import * as events from "@backend/events";

import App from "@components/App";

// Call initial setup functions.
events.setup();

// Render the application.
createRoot(document.getElementById(
    "root") as HTMLElement).render(
        <React.StrictMode>
            <App />
        </React.StrictMode>
);
