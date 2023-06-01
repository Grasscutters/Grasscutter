import React from "react";

import Character from "@app/ui/widgets/Character";

import type { Avatar } from "@backend/types";
import { listAvatars } from "@backend/data";
import { grantAvatar } from "@backend/server";

import "@css/pages/AvatarsPage.scss";

class AvatarsPage extends React.PureComponent {
    /**
     * Grants the avatar to the user.
     *
     * @param avatar The avatar to grant.
     * @private
     */
    private async grantAvatar(avatar: Avatar): Promise<void> {
        console.log(await grantAvatar(avatar.id));
    }

    render() {
        return (
            <div className={"AvatarsPage"}>
                <h1 className={"AvatarsPage_Title"}>Characters</h1>

                <div className={"AvatarsPage_List"}>
                    {listAvatars().map((avatar) =>
                        avatar.id > 11000000 ? undefined : (
                            <Character key={avatar.id} data={avatar} onClick={this.grantAvatar.bind(this, avatar)} />
                        )
                    )}
                </div>
            </div>
        );
    }
}

export default AvatarsPage;
