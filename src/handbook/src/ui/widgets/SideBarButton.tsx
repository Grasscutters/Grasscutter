import React from "react";

import type { Page } from "@backend/types";
import { navigate } from "@backend/events";

import "@css/widgets/SideBarButton.scss";

interface IProps {
    name: string;
    icon: string;
    anchor: Page;
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
        navigate(this.props.anchor);
    }

    render() {
        return (
            <div className={"SideBarButton"} onClick={() => this.redirect()}>
                <img className={"SideBarButton_Icon"} src={this.props.icon} alt={this.props.name} />

                <p className={"SideBarButton_Label"}>{this.props.name}</p>
            </div>
        );
    }
}

export default SideBarButton;
