import React from "react";

import HomeButton from "@widgets/HomeButton";

import { ReactComponent as DiscordLogo } from "@icons/discord.svg";

import Icon_Version_Highlights from "@assets/Icon_Version_Highlights.webp";
import Icon_Character_Lumine from "@assets/Icon_Character_Lumine.webp";
import Icon_Inventory from "@assets/Icon_Inventory.webp";
import Icon_Tutorial_Monster from "@assets/Icon_Tutorial_Monster.webp";
import Icon_Map from "@assets/Icon_Map.webp";
import Icon_Quests from "@assets/Icon_Quests.webp";
import Icon_Achievements from "@assets/Icon_Achievements.webp";

import { openUrl } from "@app/utils";

import "@css/pages/HomePage.scss";

class HomePage extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
    }

    render() {
        return (
            <div className={"HomePage"}>
                <div className={"HomePage_Top"}>
                    <h1 className={"HomePage_Title"}>Welcome back, Traveler~</h1>

                    <div className={"HomePage_Buttons"}>
                        <HomeButton name={"Commands"} anchor={"Commands"} icon={Icon_Version_Highlights} />
                        <HomeButton name={"Characters"} anchor={"Avatars"} icon={Icon_Character_Lumine} />
                        <HomeButton name={"Items"} anchor={"Items"} icon={Icon_Inventory} />
                        <HomeButton name={"Entities"} anchor={"Entities"} icon={Icon_Tutorial_Monster} />
                        <HomeButton name={"Scenes"} anchor={"Scenes"} icon={Icon_Map} />
                        <HomeButton name={"Quests"} anchor={"Quests"} icon={Icon_Quests} />
                        <HomeButton name={"Achievements"} anchor={"Achievements"} icon={Icon_Achievements} />
                    </div>
                </div>

                <div className={"HomePage_Bottom"}>
                    <div className={"HomePage_Box HomePage_Disclaimer"}>
                        <p>
                            <b>This tool is not affiliated with HoYoverse.</b>
                            <br />
                            Genshin Impact, game content and materials are
                            <br />
                            trademarks and copyrights of HoYoverse.
                        </p>

                        <div className={"HomePage_Discord"} onClick={() => openUrl("https://discord.gg/grasscutter")}>
                            <DiscordLogo />
                            <p>Join the Community!</p>
                        </div>
                    </div>

                    <div className={"HomePage_Text"}>
                        <div className={"HomePage_Credits"}>
                            <p>Credits</p>
                            <p>(hover to see info)</p>
                        </div>

                        <div className={"HomePage_Links"}>
                            <a href={"https://paimon.moe"}>paimon.moe</a>
                            <a href={"https://gitlab.com/Dimbreath/AnimeGameData"}>Anime Game Data</a>
                            <a href={"https://genshin-impact.fandom.com"}>Genshin Impact Wiki</a>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default HomePage;
