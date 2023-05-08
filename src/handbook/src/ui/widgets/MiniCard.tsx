import React from "react";

import { itemIcon } from "@app/utils";

import "@css/widgets/MiniCard.scss";

interface IProps {
    data: { name: string };
    icon: string;

    onClick?: () => void;
}

interface IState {
    popout: boolean;
    icon: boolean;
    loaded: boolean;
}

class MiniCard extends React.Component<IProps, IState> {
    loading: number | any;

    constructor(props: IProps) {
        super(props);

        this.state = {
            popout: false,
            icon: true,
            loaded: false
        };
    }

    /**
     * Replaces the icon with the item's name.
     * @private
     */
    private replaceIcon(): void {
        this.setState({ icon: false, loaded: false });
    }

    private forceReplace(): void {
        if (!this.state.loaded) this.replaceIcon();
    }

    componentDidMount() {
        this.loading = setTimeout(this.forceReplace.bind(this), 1e3);
    }

    componentWillUnmount() {
        clearTimeout(this.loading);
        this.loading = null;
    }

    render() {
        return (
            <div className={"MiniCard"} onClick={this.props.onClick}>
                <div className={"MiniCard_Background"}>
                    {this.state.icon && (
                        <img
                            className={"MiniCard_Icon"}
                            alt={this.props.data.name}
                            src={this.props.icon}
                            onError={this.replaceIcon.bind(this)}
                            onLoad={() => this.setState({ loaded: true })}
                        />
                    )}

                    {(!this.state.loaded || !this.state.icon) && (
                        <p className={"MiniCard_Label"}>{this.props.data.name}</p>
                    )}
                </div>
            </div>
        );
    }
}

export default MiniCard;
