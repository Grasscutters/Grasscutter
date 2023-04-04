import React from "react";

import SideBar from "@views/SideBar";
import Content from "@views/Content";

import "@css/App.scss";
import "@css/Text.scss";

// Based on the design at: https://www.figma.com/file/PDeAVDkTDF5vvUGGdaIZ39/GM-Handbook.
// Currently designed by: Magix.

class App extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"App"}>
                <SideBar />
                <Content />
            </div>
        );
    }
}

export default App;
