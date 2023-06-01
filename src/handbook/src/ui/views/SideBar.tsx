import React, { ChangeEvent } from "react";

import SideBarButton from "@app/ui/widgets/SideBarButton";

import Icon_Version_Highlights from "@assets/Icon_Version_Highlights.webp";
import Icon_Character_Lumine from "@assets/Icon_Character_Lumine.webp";
import Icon_Inventory from "@assets/Icon_Inventory.webp";
import Icon_Tutorial_Monster from "@assets/Icon_Tutorial_Monster.webp";
import Icon_Map from "@assets/Icon_Map.webp";
import Icon_Quests from "@assets/Icon_Quests.webp";
import Icon_Achievements from "@assets/Icon_Achievements.webp";

import events, { navigate } from "@backend/events";
import { targetPlayer, lockedPlayer, setTargetPlayer } from "@backend/server";

import "@css/views/SideBar.scss";

interface IState {
    uid: string | null;
    uidLocked: boolean;
}

class SideBar extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            uid: targetPlayer > 0 ? targetPlayer.toString() : null,
            uidLocked: lockedPlayer
        };
    }

    /**
     * Invoked when the player's UID changes.
     * @private
     */
    private updateUid(): void {
        this.setState({
            uid: targetPlayer > 0 ? targetPlayer.toString() : null,
            uidLocked: lockedPlayer
        });
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

        setTargetPlayer(parseInt(uid ?? "0"));
    }

    /**
     * Invoked when the UID input is right-clicked.
     *
     * @param event The event.
     * @private
     */
    private onRightClick(event: React.MouseEvent<HTMLInputElement, MouseEvent>): void {
        // Remove focus from the input.
        event.currentTarget.blur();
        event.preventDefault();

        // Open the server settings overlay.
        events.emit("overlay", "ServerSettings");
    }

    componentDidMount() {
        events.on("connected", this.updateUid.bind(this));
    }

    componentWillUnmount() {
        events.off("connected", this.updateUid.bind(this));
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
                        <SideBarButton name={"Commands"} anchor={"Commands"} icon={Icon_Version_Highlights} />
                        <SideBarButton name={"Characters"} anchor={"Avatars"} icon={Icon_Character_Lumine} />
                        <SideBarButton name={"Items"} anchor={"Items"} icon={Icon_Inventory} />
                        <SideBarButton name={"Entities"} anchor={"Entities"} icon={Icon_Tutorial_Monster} />
                        <SideBarButton name={"Scenes"} anchor={"Scenes"} icon={Icon_Map} />
                        <SideBarButton name={"Quests"} anchor={"Quests"} icon={Icon_Quests} />
                        <SideBarButton name={"Achievements"} anchor={"Achievements"} icon={Icon_Achievements} />
                    </div>

                    <div className={"SideBar_Enter"}>
                        <input
                            type={"text"}
                            className={"SideBar_Input"}
                            placeholder={"Enter UID..."}
                            value={this.state.uid ?? undefined}
                            disabled={this.state.uidLocked}
                            onChange={this.onChange.bind(this)}
                            onContextMenu={this.onRightClick.bind(this)}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default SideBar;
