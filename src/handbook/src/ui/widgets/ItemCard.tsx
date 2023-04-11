import React from "react";

import type { Item as ItemType, ItemInfo } from "@backend/types";
import { itemTypeToString } from "@backend/types";
import { itemIcon } from "@app/utils";
import { giveItem } from "@backend/server";

import "@css/widgets/ItemCard.scss";

/**
 * Converts a description string into a list of paragraphs.
 *
 * @param description The description to convert.
 */
function toDescription(description: string | undefined): JSX.Element[] {
    if (!description) return [];

    return description.split("\\n").map((line, index) => {
        return <p key={index}>{line}</p>;
    });
}

interface IProps {
    item: ItemType | null;
    info: ItemInfo | null;
}

interface IState {
    icon: boolean;
    count: number | string;
}

const defaultState = {
    icon: true,
    count: 1
};

class ItemCard extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = defaultState;
    }

    /**
     * Updates the count of the item.
     *
     * @param event The change event.
     * @private
     */
    private updateCount(event: React.ChangeEvent<HTMLInputElement>) {
        const value = event.target.value;
        if (isNaN(parseInt(value)) && value.length > 1) return;

        this.setState({ count: value });
    }

    /**
     * Adds to the count of the item.
     *
     * @param positive Is the count being added or subtracted?
     * @param multiple Is the count being multiplied by 10?
     * @private
     */
    private addCount(positive: boolean, multiple: boolean) {
        let { count } = this.state;
        if (count === "") count = 1;
        if (typeof count == "string") count = parseInt(count);
        if (count < 1) count = 1;

        let increment = 1;
        if (!positive) increment = -1;
        if (multiple) increment *= 10;

        count = Math.max(1, count + increment);

        this.setState({ count });
    }

    /**
     * Adds the item to the player's connected inventory.
     * @private
     */
    private async addToInventory(): Promise<void> {
        await giveItem(
            this.props.item?.id ?? 102,
            typeof this.state.count == "string" ? parseInt(this.state.count) : this.state.count
        );
    }

    componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
        if (this.props.item != prevProps.item) {
            this.setState(defaultState);
        }
    }

    render() {
        const { item, info } = this.props;
        const data = info?.data;

        return item ? (
            <div className={"ItemCard"}>
                <div className={"ItemCard_Content"}>
                    <div className={"ItemCard_Header"}>
                        <div className={"ItemCard_Info"}>
                            <p>{data?.name ?? item.name}</p>
                            <p>{data?.type ?? itemTypeToString(item.type)}</p>
                        </div>

                        {this.state.icon && (
                            <img
                                className={"ItemCard_Icon"}
                                alt={item.name}
                                src={itemIcon(item)}
                                onError={() => this.setState({ icon: false })}
                            />
                        )}
                    </div>

                    <div className={"ItemCard_Description"}>{toDescription(data?.description)}</div>
                </div>

                <div className={"ItemCard_Actions"}>
                    <div className={"ItemCard_Counter"}>
                        <div
                            onClick={() => this.addCount(false, false)}
                            onContextMenu={(e) => {
                                e.preventDefault();
                                this.addCount(false, true);
                            }}
                            className={"ItemCard_Operation"}
                        >
                            -
                        </div>
                        <input
                            type={"text"}
                            value={this.state.count}
                            className={"ItemCard_Count"}
                            onChange={this.updateCount.bind(this)}
                            onBlur={() => {
                                if (this.state.count == "") {
                                    this.setState({ count: 1 });
                                }
                            }}
                        />
                        <div
                            onClick={() => this.addCount(true, false)}
                            onContextMenu={(e) => {
                                e.preventDefault();
                                this.addCount(true, true);
                            }}
                            className={"ItemCard_Operation"}
                        >
                            +
                        </div>
                    </div>

                    <button className={"ItemCard_Submit"} onClick={this.addToInventory.bind(this)}>
                        Add to Inventory
                    </button>
                </div>
            </div>
        ) : undefined;
    }
}

export default ItemCard;
