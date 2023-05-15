import React from "react";

import type { Entity as EntityType, EntityInfo } from "@backend/types";
import { copyToClipboard, entityIcon, notNaN } from "@app/utils";
import { connected, spawnEntity } from "@backend/server";
import { spawn } from "@backend/commands";

import "@css/widgets/ObjectCard.scss";

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
    level: number | string;

    showingCount: boolean;
}

const defaultState = {
    icon: true,
    count: 1,
    level: 1,
    showingCount: true
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
        let value = event.target.value;
        // Remove non-numeric characters.
        value = value.replace(/[^0-9]/g, "");

        let numeric = parseInt(value);
        if (isNaN(numeric) && value.length > 1) return;

        // Check if the value should be a level.
        if (!this.state.showingCount && numeric > 200) numeric = 200;

        const updated: any = this.state.showingCount ? { count: numeric } : { level: numeric };
        this.setState(updated);
    }

    /**
     * Adds to the count of the entity.
     *
     * @param positive Is the count being added or subtracted?
     * @param multiple Is the count being multiplied by 10?
     * @private
     */
    private addCount(positive: boolean, multiple: boolean) {
        let value = this.state.showingCount ? this.state.count : this.state.level;
        if (value === "") value = 1;
        if (typeof value == "string") value = parseInt(value);
        if (value < 1) value = 1;

        let increment = 1;
        if (!positive) increment = -1;
        if (multiple) increment *= 10;

        value = Math.max(1, value + increment);
        // Check if the value should be a level.
        if (!this.state.showingCount && value > 200) value = 200;

        const updated: any = this.state.showingCount ? { count: value } : { level: value };
        this.setState(updated);
    }

    /**
     * Summons the entity at the connected player's position.
     * @private
     */
    private async summonAtPlayer(): Promise<void> {
        const entity = this.props.entity?.id ?? 21010101;
        const amount = typeof this.state.count == "string" ? parseInt(this.state.count) : this.state.count;
        const level = typeof this.state.level == "string" ? parseInt(this.state.level) : this.state.level;

        if (connected) {
            await spawnEntity(entity, amount, level);
        } else {
            await copyToClipboard(spawn.monster(entity, amount, level));
        }
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
            <div className={"ObjectCard"}>
                <div className={"ObjectCard_Content"}>
                    <div className={"ObjectCard_Header"}>
                        <div className={"ObjectCard_Info"}>
                            <p>{data?.name ?? entity.name}</p>
                            <p>{data?.type ?? ""}</p>
                        </div>

                        {this.state.icon && (
                            <img
                                className={"ObjectCard_Icon"}
                                alt={entity.name}
                                src={entityIcon(entity)}
                                onError={() => this.setState({ icon: false })}
                            />
                        )}
                    </div>

                    <div className={"ObjectCard_Description"}>{toDescription(data?.description)}</div>
                </div>

                <div className={"ObjectCard_Actions"}>
                    <div className={"ObjectCard_Counter"}>
                        <div
                            onClick={() => this.addCount(false, false)}
                            onContextMenu={(e) => {
                                e.preventDefault();
                                this.addCount(false, true);
                            }}
                            className={"ObjectCard_Operation"}
                        >
                            -
                        </div>
                        <input
                            type={"text"}
                            value={
                                this.state.showingCount
                                    ? `x${notNaN(this.state.count)}`
                                    : `Lv${notNaN(this.state.level)}`
                            }
                            className={"ObjectCard_Count"}
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
                            className={"ObjectCard_Operation"}
                        >
                            +
                        </div>
                    </div>

                    <button
                        className={"ObjectCard_Submit"}
                        onClick={this.summonAtPlayer.bind(this)}
                        onContextMenu={(e) => {
                            e.preventDefault();
                            this.setState({ showingCount: !this.state.showingCount });
                        }}
                    >
                        Summon
                    </button>
                </div>
            </div>
        ) : undefined;
    }
}

export default EntityCard;
