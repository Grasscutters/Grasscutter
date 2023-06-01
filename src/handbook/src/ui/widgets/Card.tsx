import React from "react";

import "@css/widgets/Card.scss";

interface IProps {
    title: string;
    alternate?: string;
    description?: string | string[];

    height?: number | string;
    button?: React.ReactNode;
    rightOffset?: number;

    onClick?: () => void;
    onOver?: () => void;
    onOut?: () => void;
}

class Card extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        return (
            <div
                className={"Card"}
                onClick={this.props.onClick}
                onMouseOver={this.props.onOver}
                onMouseOut={this.props.onOut}
                style={{
                    height: this.props.height,
                    cursor: this.props.onClick ? "pointer" : undefined
                }}
            >
                <div className={"Card_Content"}>
                    <div className={"Card_Header"}>
                        <p className={"Card_Title"}>{this.props.title}</p>
                        {this.props.alternate && <p className={"Card_Alternate"}>{this.props.alternate}</p>}
                    </div>

                    <div style={{ alignItems: "center" }}>
                        {this.props.description ? (
                            Array.isArray(this.props.description) ? (
                                this.props.description.map((line, index) => (
                                    <p className={"Card_Description"} key={index}>
                                        {line}
                                    </p>
                                ))
                            ) : (
                                <p className={"Card_Description"}>{this.props.description}</p>
                            )
                        ) : undefined}
                    </div>
                </div>

                {this.props.button ? (
                    <div className={"Card_Button"} style={{ marginRight: this.props.rightOffset ?? 0 }}>
                        {this.props.button}
                    </div>
                ) : undefined}
            </div>
        );
    }
}

export default Card;
