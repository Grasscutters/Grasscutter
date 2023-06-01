import React from "react";

import SideBar from "@views/SideBar";
import Content from "@views/Content";
import Overlay from "@views/Overlay";
import PlainText from "@views/PlainText";

import type { Page } from "@backend/types";
import { isPage } from "@backend/types";

import "@css/App.scss";
import "@css/Text.scss";

// Based on the design at: https://www.figma.com/file/PDeAVDkTDF5vvUGGdaIZ39/GM-Handbook.
// Currently designed by: Magix.

interface IState {
    initial: Page | null;
    plain: boolean;
}

class App extends React.Component<{}, IState> {
    constructor(props: any) {
        super(props);

        // Check if the window's href is a page.
        let targetPage = null;
        const page = window.location.href.split("/").pop();

        if (page != undefined && page != "") {
            // Convert the page to a Page type.
            const pageName = page.charAt(0).toUpperCase() + page.slice(1);
            // Check if the page is a valid page.
            if (isPage(pageName)) targetPage = pageName as Page;
        }

        this.state = {
            initial: targetPage as Page | null,
            plain: false
        };
    }

    render() {
        return (
            <div className={"App"}>
                <SideBar />

                {this.state.plain ? <PlainText /> : <Content initial={this.state.initial} />}

                <Overlay />
            </div>
        );
    }
}

export default App;
