import React from "react";

import "@css/views/SideBar.scss";
import SideBarButton from "@app/ui/widgets/SideBarButton";

class SideBar extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"SideBar"}>
                <h1 className={"SideBar_Title"}>The Ultimate Anime Game Handbook</h1>

                <div className={"SideBar_Buttons"}>
                    <SideBarButton name={"Commands"} anchor={"Commands"} />
                    <SideBarButton name={"Characters"} anchor={"Home"} />
                    <SideBarButton name={"Items"} anchor={"Home"} />
                    <SideBarButton name={"Entities"} anchor={"Home"} />
                    <SideBarButton name={"Scenes"} anchor={"Home"} />
                    <SideBarButton name={"Quests"} anchor={"Home"} />
                    <SideBarButton name={"Achievements"} anchor={"Home"} />
                </div>
            </div>
        );
    }
}

export default SideBar;
