import React from "react";

import Character from "@app/ui/widgets/Character";

import { listAvatars } from "@backend/data";

import "@css/pages/AvatarsPage.scss";

class AvatarsPage extends React.PureComponent {
    render() {
        return (
            <div className={"AvatarsPage"}>
                <h1 className={"AvatarsPage_Title"}>Characters</h1>

                <div className={"AvatarsPage_List"}>
                    {listAvatars().map((avatar) =>
                        avatar.id > 11000000 ? undefined : <Character key={avatar.id} data={avatar} />
                    )}
                </div>
            </div>
        );
    }
}

export default AvatarsPage;
