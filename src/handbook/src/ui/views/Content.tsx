import React from "react";

import "@css/views/Content.scss";
import HomeButton from "@app/ui/widgets/HomeButton";

class Content extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"Content"}>
                <div className={"Content_Top"}>
                    <h1 className={"Content_Title"}>Welcome Back Traveler!</h1>

                    <div className={"Content_Buttons"}>
                        <HomeButton name={"Commands"} anchor={"commands"} />
                    </div>
                </div>

                <div className={"Content_Bottom"}>

                </div>
            </div>
        );
    }
}

export default Content;
