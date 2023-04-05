import React from "react";

import HomeButton from "@app/ui/widgets/HomeButton";

import { ReactComponent as DiscordLogo } from "@icons/discord.svg";

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
                        <HomeButton name={"Commands"} anchor={"commands"} />
                        <HomeButton name={"Characters"} anchor={"avatars"} />
                        <HomeButton name={"Items"} anchor={"items"} />
                        <HomeButton name={"Entities"} anchor={"monsters"} />
                        <HomeButton name={"Scenes"} anchor={"scenes"} />
                    </div>

                    <div className={"HomePage_Buttons"}>
                        <HomeButton name={"Quests"} anchor={"quests"} />
                        <HomeButton name={"Achievements"} anchor={"achievements"} />
                    </div>
                </div>

                <div className={"HomePage_Bottom"}>
                    <div className={"HomePage_Box HomePage_Disclaimer"}>
                        <div>
                            <p>This tool is not affiliated with HoYoverse.</p>
                            <p>Genshin Impact, game HomePage and materials are</p>
                            <p>trademarks and copyrights of HoYoverse.</p>
                        </div>

                        <div className={"HomePage_Discord"}>
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
