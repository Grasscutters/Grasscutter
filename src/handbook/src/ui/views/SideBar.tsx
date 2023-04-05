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
                    <SideBarButton name={"Commands"} anchor={"commands"} />
                    <SideBarButton name={"Characters"} anchor={"avatars"} />
                    <SideBarButton name={"Items"} anchor={"items"} />
                    <SideBarButton name={"Entities"} anchor={"monsters"} />
                    <SideBarButton name={"Scenes"} anchor={"scenes"} />
                    <SideBarButton name={"Quests"} anchor={"quests"} />
                    <SideBarButton name={"Achievements"} anchor={"achievements"} />
                </div>
            </div>
        );
    }
}

export default SideBar;
