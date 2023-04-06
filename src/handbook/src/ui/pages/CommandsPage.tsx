import React from "react";

import "@css/pages/CommandsPage.scss";

class CommandsPage extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"CommandsPage"}>
                <h1>Commands</h1>
            </div>
        );
    }
}

export default CommandsPage;
