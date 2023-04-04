import React from "react";

import HomeButton from "@app/ui/widgets/HomeButton";

import { ReactComponent as DiscordLogo } from "@icons/discord.svg";

import "@css/views/Content.scss";

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
                        <HomeButton name={"Characters"} anchor={"avatars"} />
                        <HomeButton name={"Items"} anchor={"items"} />
                        <HomeButton name={"Entities"} anchor={"monsters"} />
                        <HomeButton name={"Scenes"} anchor={"scenes"} />
                    </div>

                    <div className={"Content_Buttons"}>
                        <HomeButton name={"Quests"} anchor={"quests"} />
                        <HomeButton name={"Achievements"} anchor={"achievements"} />
                    </div>
                </div>

                <div className={"Content_Bottom"}>
                    <div className={"Content_Box Content_Disclaimer"}>
                        <div>
                            <p>This tool is not affiliated with HoYoverse.</p>
                            <p>Genshin Impact, game content and materials are</p>
                            <p>trademarks and copyrights of HoYoverse.</p>
                        </div>

                        <div className={"Content_Discord"}>
                            <DiscordLogo />
                            <p>Join the Community!</p>
                        </div>
                    </div>

                    <div className={"Content_Text"}>

                    </div>
                </div>
            </div>
        );
    }
}

export default Content;
