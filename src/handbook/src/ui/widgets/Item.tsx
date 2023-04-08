import React from "react";

import type { Item as ItemData } from "@backend/types";

import "@css/widgets/Item.scss";

interface IProps {
    data: ItemData;
}

interface IState {
    popout: boolean;
}

class Item extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            popout: false
        };
    }

    /**
     * Fetches the icon for the item.
     * @private
     */
    private getIcon(): string {
        return `https://paimon.moe/images/items/teachings_of_freedom.png`;
    }

    render() {
        return (
            <div className={"Item"}>
                <img
                    className={"Item_Icon"}
                    alt={this.props.data.name}
                    src={this.getIcon()}
                />

                <div className={"Item_Info"}>

                </div>
            </div>
        );
    }
}

export default Item;
