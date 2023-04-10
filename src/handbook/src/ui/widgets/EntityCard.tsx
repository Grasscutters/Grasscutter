import React from "react";

import type { Entity as EntityType, EntityInfo } from "@backend/types";
import { entityIcon } from "@app/utils";

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
    entity: EntityType | null;
    info: EntityInfo | null;
}

interface IState {
    icon: boolean;
    count: number | string;
}

const defaultState = {
    icon: true,
    count: 1
};

class EntityCard extends React.Component<IProps, IState> {
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
     * Adds to the count of the entity.
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
     * Summons the entity at the connected player's position.
     * @private
     */
    private async summonAtPlayer(): Promise<void> {
        // TODO: Implement server access.
    }

    componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
        if (this.props.entity != prevProps.entity) {
            this.setState(defaultState);
        }
    }

    render() {
        const { entity, info } = this.props;
        const data = info?.data;

        return entity ? (
            <div className={"ItemCard"}>
                <div className={"ItemCard_Content"}>
                    <div className={"ItemCard_Header"}>
                        <div className={"ItemCard_Info"}>
                            <p>{data?.name ?? entity.name}</p>
                            <p>{data?.type ?? ""}</p>
                        </div>

                        {this.state.icon && (
                            <img
                                className={"ItemCard_Icon"}
                                alt={entity.name}
                                src={entityIcon(entity)}
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

                    <button className={"ItemCard_Submit"} onClick={this.summonAtPlayer.bind(this)}>
                        Summon
                    </button>
                </div>
            </div>
        ) : undefined;
    }
}

export default EntityCard;
