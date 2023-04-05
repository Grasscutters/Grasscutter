import React from "react";

import "@css/widgets/SideBarButton.scss";

interface IProps {
    name: string;
    anchor: string;
}

class SideBarButton extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    /**
     * Redirects the user to the specified anchor.
     * @private
     */
    private redirect(): void {
        // TODO: Implement navigator system.
        window.location.href = `/${this.props.anchor}`;
    }

    render() {
        return (
            <div
                className={"SideBarButton"}
                onClick={() => this.redirect()}
            >
                <img
                    className={"SideBarButton_Icon"}
                    src={"https://dummyimage.com/128x128"}
                    alt={this.props.name}
                />

                <p className={"SideBarButton_Label"}>{this.props.name}</p>
            </div>
        );
    }
}

export default SideBarButton;
