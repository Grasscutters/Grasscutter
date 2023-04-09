import React from "react";

import type { Item as ItemData } from "@backend/types";
import { itemIcon } from "@app/utils";

import "@css/widgets/Item.scss";

interface IProps {
    data: ItemData;
}

interface IState {
    popout: boolean;
    icon: boolean;
}

class Item extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            popout: false,
            icon: true
        };
    }

    /**
     * Replaces the icon with the item's name.
     * @private
     */
    private replaceIcon(): void {
        this.setState({ icon: false });
    }

    render() {
        return (
            <div className={"Item"}>
                <div className={"Item_Background"}>
                    {
                        this.state.icon ? (
                            <img
                                className={"Item_Icon"}
                                alt={this.props.data.name}
                                src={itemIcon(this.props.data)}
                                onError={this.replaceIcon.bind(this)}
                            />
                        ) : <p className={"Item_Label"}>{this.props.data.name}</p>
                    }
                </div>

                <div className={"Item_Info"}>

                </div>
            </div>
        );
    }
}

export default Item;
