import React from "react";

import ServerSettings from "@widgets/ServerSettings";

import type { Overlays } from "@backend/types";

import "@css/views/Overlay.scss";
import events from "@backend/events";

interface IState {
    page: Overlays;
}

class Overlay extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            page: "None"
        };
    }

    /**
     * Sets the page to display.
     *
     * @param page The page to display.
     */
    private setPage(page: Overlays): void {
        this.setState({ page });
    }

    /**
     * Gets the page to display.
     */
    private getPage(): React.ReactNode {
        switch (this.state.page) {
            default:
                return undefined;
            case "ServerSettings":
                return <ServerSettings />;
        }
    }

    componentDidMount() {
        events.on("overlay", this.setPage.bind(this));
    }

    componentWillUnmount() {
        events.off("overlay", this.setPage.bind(this));
    }

    render() {
        return this.state.page != "None" ? <div className={"Overlay"}>{this.getPage()}</div> : undefined;
    }
}

export default Overlay;
