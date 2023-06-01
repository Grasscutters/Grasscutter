import React from "react";

import type { Avatar } from "@backend/types";
import { colorFor, formatAvatarName } from "@app/utils";

import "@css/widgets/Character.scss";

// Image base URL: https://paimon.moe/images/characters/(name).png

const ignored = [
    10000001 // Kate
];

const nameSwitch: { [key: number]: string } = {
    10000005: "Lumine",
    10000007: "Aether"
};

interface IProps {
    data: Avatar;

    onClick?: () => void;
}

class Character extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        const { name, quality, id } = this.props.data;
        const qualityColor = colorFor(quality);

        // Check if the avatar is blacklisted.
        if (ignored.includes(id)) return undefined;

        const characterName = nameSwitch[id] ?? name;

        return (
            <div className={"Character"} onClick={this.props.onClick}>
                <img
                    className={"Character_Icon"}
                    alt={name}
                    src={`https://paimon.moe/images/characters/${formatAvatarName(name, id)}.png`}
                    style={{ backgroundColor: `var(${qualityColor})` }}
                />

                <div className={"Character_Label"}>
                    <p style={{ fontSize: characterName.length >= 10 ? 13 : 17 }}>{characterName}</p>
                </div>
            </div>
        );
    }
}

export default Character;
