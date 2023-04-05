import React from "react";

import "@css/widgets/Character.scss";

// Image base URL: https://paimon.moe/images/characters/(name).png

/**
 * Formats a character's name to fit with the reference name.
 * Example: Hu Tao -> hu_tao
 *
 * @param name The character's name.
 */
function formatName(name: string): string {
    return name.toLowerCase().replace(" ", "_");
}

interface IProps {
    name: string; // paimon.moe reference name.
}

class Character extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        return (
            <div className={"Character"}>
                <img
                    className={"Character_Icon"}
                    alt={this.props.name}
                    src={`https://paimon.moe/images/characters/${formatName(this.props.name)}.png`}
                />

                <div className={"Character_Label"}>
                    <p>{this.props.name}</p>
                </div>
            </div>
        );
    }
}

export default Character;
