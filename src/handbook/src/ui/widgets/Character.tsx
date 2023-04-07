import React from "react";

import type { Avatar } from "@backend/types";
import { colorFor } from "@app/utils";

import "@css/widgets/Character.scss";

// Image base URL: https://paimon.moe/images/characters/(name).png

/**
 * Formats a character's name to fit with the reference name.
 * Example: Hu Tao -> hu_tao
 *
 * @param name The character's name.
 * @param id The character's ID.
 */
function formatName(name: string, id: number): string {
    // Check if a different name is used for the character.
    if (refSwitch[id]) name = refSwitch[id];
    return name.toLowerCase().replace(" ", "_");
}

const ignored = [
    10000001 // Kate
];

const refSwitch: { [key: number]: string } = {
    10000005: "traveler_anemo",
    10000007: "traveler_geo",
};

const nameSwitch: { [key: number]: string } = {
    10000005: "Lumine",
    10000007: "Aether",
};

interface IProps {
    data: Avatar;
}

class Character extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        const { name, quality, id } = this.props.data;
        const qualityColor = colorFor(quality);

        // Check if the avatar is blacklisted.
        if (ignored.includes(id))
            return undefined;

        return (
            <div
                className={"Character"}
                style={{ backgroundColor: `var(${qualityColor})` }}
            >
                <img
                    className={"Character_Icon"}
                    alt={name}
                    src={`https://paimon.moe/images/characters/${formatName(name, id)}.png`}
                />

                <div className={"Character_Label"}>
                    <p>{nameSwitch[id] ?? name}</p>
                </div>
            </div>
        );
    }
}

export default Character;
