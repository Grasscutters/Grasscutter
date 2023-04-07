import React from "react";

import type { Page } from "@backend/types";
import { navigate } from "@backend/events";

import "@css/widgets/HomeButton.scss";

interface IProps {
    name: string;
    anchor: Page;
}

class HomeButton extends React.PureComponent<IProps> {
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
            <div className={"HomeButton"} onClick={() => this.redirect()}>
                <img className={"HomeButton_Icon"} src={"https://dummyimage.com/128x128"} alt={this.props.name} />

                <p className={"HomeButton_Label"}>{this.props.name}</p>
            </div>
        );
    }
}

export default HomeButton;
