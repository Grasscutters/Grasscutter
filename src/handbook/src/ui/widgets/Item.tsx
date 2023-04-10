import React from "react";

import type { Item as ItemData } from "@backend/types";
import { itemIcon } from "@app/utils";

import "@css/widgets/Item.scss";

interface IProps {
    data: ItemData;
    onClick?: () => void;
}

interface IState {
    popout: boolean;
    icon: boolean;
    loaded: boolean;
}

class Item extends React.Component<IProps, IState> {
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
            <div className={"Item"} onClick={this.props.onClick}>
                <div className={"Item_Background"}>
                    {this.state.icon && (
                        <img
                            className={"Item_Icon"}
                            alt={this.props.data.name}
                            src={itemIcon(this.props.data)}
                            onError={this.replaceIcon.bind(this)}
                            onLoad={() => this.setState({ loaded: true })}
                        />
                    )}

                    {(!this.state.loaded || !this.state.icon) && <p className={"Item_Label"}>{this.props.data.name}</p>}
                </div>

                <div className={"Item_Info"}></div>
            </div>
        );
    }
}

export default Item;
