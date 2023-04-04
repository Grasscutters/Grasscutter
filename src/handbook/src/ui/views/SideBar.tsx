import React from "react";

import "@css/views/SideBar.scss";

class SideBar extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"SideBar"}>
                <p>hi!</p>
            </div>
        );
    }
}

export default SideBar;
