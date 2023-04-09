import React from "react";

import type { Item as ItemType, ItemInfo } from "@backend/types";
import { itemTypeToString } from "@backend/types";
import { itemIcon } from "@app/utils";

import "@css/widgets/ItemCard.scss";

/**
 * Converts a description string into a list of paragraphs.
 *
 * @param description The description to convert.
 */
function toDescription(description: string | undefined): JSX.Element[] {
    if (!description) return [];

    return description.split("\\n")
        .map((line, index) => {
            return <p key={index}>{line}</p>;
        });
}

interface IProps {
    item: ItemType | null;
    info: ItemInfo | null;
}

interface IState {
    icon: boolean;
}

class ItemCard extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            icon: true
        };
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

                        { this.state.icon && <img
                            className={"ItemCard_Icon"}
                            alt={item.name}
                            src={itemIcon(item)}
                            onError={() => this.setState({ icon: false })}
                        /> }
                    </div>

                    <div className={"ItemCard_Description"}>
                        {toDescription(data?.description)}
                    </div>
                </div>

                <div>

                </div>
            </div>
        ) : undefined;
    }
}

export default ItemCard;
