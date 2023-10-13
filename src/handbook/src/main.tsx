// Import React and createRoot function
import React from "react";
import { createRoot } from "react-dom/client";

// Import backend modules
import * as data from "@backend/data";
import * as events from "@backend/events";
import * as server from "@backend/server";

// Import the application component
import App from "@ui/App";

// Call initial setup functions
data.setup(); // Set up data
events.setup(); // Set up events
server.setup(); // Set up server

// Render the application
createRoot(document.getElementById("root") as HTMLElement).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);