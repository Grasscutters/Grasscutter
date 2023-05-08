import React from "react";
import { createRoot } from "react-dom/client";

import * as data from "@backend/data";
import * as events from "@backend/events";

import App from "@ui/App";

// Call initial setup functions.
data.setup();
events.setup();

// Render the application.
createRoot(document.getElementById("root") as HTMLElement).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
