import React from "react";

import SideBarButton from "@app/ui/widgets/SideBarButton";

import { navigate } from "@app/backend/events";

import "@css/views/SideBar.scss";

class SideBar extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"SideBar"}>
                <h1 className={"SideBar_Title"} onClick={() => navigate("Home")}>
                    The Ultimate Anime Game Handbook
                </h1>

                <div className={"SideBar_Buttons"}>
                    <SideBarButton name={"Commands"} anchor={"Commands"} />
                    <SideBarButton name={"Characters"} anchor={"Avatars"} />
                    <SideBarButton name={"Items"} anchor={"Items"} />
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
