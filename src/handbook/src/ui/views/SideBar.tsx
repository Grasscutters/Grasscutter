import React, { ChangeEvent } from "react";

import SideBarButton from "@app/ui/widgets/SideBarButton";

import { navigate } from "@app/backend/events";

import "@css/views/SideBar.scss";
import { setTargetPlayer } from "@backend/server";

interface IState {
    uid: string | null;
}

class SideBar extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            uid: null
        };
    }

    /**
     * Invoked when the UID input changes.
     *
     * @param event The event.
     * @private
     */
    private onChange(event: ChangeEvent<HTMLInputElement>): void {
        const input = event.target.value;
        const uid = input == "" ? null : input;
        if (uid && uid.length > 10) return;

        this.setState({ uid });
        setTargetPlayer(parseInt(uid ?? "0"));
    }

    render() {
        return (
            <div className={"SideBar"}>
                <h1 className={"SideBar_Title"} onClick={() => navigate("Home")}>
                    The Ultimate Anime Game Handbook
                </h1>

                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-between",
                        height: "100%"
                    }}
                >
                    <div className={"SideBar_Buttons"}>
                        <SideBarButton name={"Commands"} anchor={"Commands"} icon={"Icon_Version_Highlights"} />
                        <SideBarButton name={"Characters"} anchor={"Avatars"} icon={"Icon_Character_Lumine"} />
                        <SideBarButton name={"Items"} anchor={"Items"} icon={"Icon_Inventory"} />
                        <SideBarButton name={"Entities"} anchor={"Entities"} icon={"Icon_Tutorial_Monster"} />
                        <SideBarButton name={"Scenes"} anchor={"Scenes"} icon={"Icon_Map"} />
                        <SideBarButton name={"Quests"} anchor={"Home"} icon={"Icon_Quests"} />
                        <SideBarButton name={"Achievements"} anchor={"Home"} icon={"Icon_Achievements"} />
                    </div>

                    <div className={"SideBar_Enter"}>
                        <input
                            type={"text"}
                            className={"SideBar_Input"}
                            placeholder={"Enter UID..."}
                            value={this.state.uid ?? undefined}
                            onChange={this.onChange.bind(this)}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default SideBar;
