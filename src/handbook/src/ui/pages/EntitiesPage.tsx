import React, { ChangeEvent } from "react";

import MiniCard from "@widgets/MiniCard";
import VirtualizedGrid from "@components/VirtualizedGrid";

import { Entity, ItemCategory } from "@backend/types";
import type { Entity as EntityType, EntityInfo } from "@backend/types";
import { getEntities } from "@backend/data";
import { entityIcon, fetchEntityData } from "@app/utils";

import "@css/pages/EntitiesPage.scss";
import EntityCard from "@widgets/EntityCard";

interface IState {
    filters: ItemCategory[];
    search: string;

    selected: EntityType | null;
    selectedInfo: EntityInfo | null;
}

class EntitiesPage extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            filters: [],
            search: "",

            selected: null,
            selectedInfo: null
        };
    }

    /**
     * Should the entity be shown?
     *
     * @param entity The entity.
     * @private
     */
    private showEntity(entity: Entity): boolean {
        // Check if the entity's name starts with N/A.
        if (entity.name.includes("[N/A]")) return false;

        return entity.id > 0;
    }

    /**
     * Gets the items to render.
     * @private
     */
    private getEntities(): EntityType[] {
        let entities: EntityType[] = [];

        // Add items based on filters.
        const filters = this.state.filters;
        if (filters.length == 0) {
            entities = getEntities();
        } else {
            for (const filter of filters) {
                // Remove duplicate items.
                entities = entities.filter((item, index) => {
                    return entities.indexOf(item) == index;
                });
            }
        }

        // Filter out items that don't match the search.
        const search = this.state.search.toLowerCase();
        if (search != "") {
            entities = entities.filter((item) => {
                return item.name.toLowerCase().includes(search);
            });
        }

        return entities;
    }

    /**
     * Invoked when the search input changes.
     *
     * @param event The event.
     * @private
     */
    private onChange(event: ChangeEvent<HTMLInputElement>): void {
        this.setState({ search: event.target.value });
    }

    /**
     * Sets the selected entity.
     *
     * @param entity The entity.
     * @private
     */
    private async setSelectedItem(entity: EntityType): Promise<void> {
        let data: EntityInfo | null = null;
        try {
            data = await fetchEntityData(entity);
        } catch {}

        this.setState({
            selected: entity,
            selectedInfo: data
        });
    }

    render() {
        const entities = this.getEntities();

        return (
            <div className={"EntitiesPage"}>
                <div className={"EntitiesPage_Content"}>
                    <div className={"EntitiesPage_Header"}>
                        <h1 className={"EntitiesPage_Title"}>Monsters</h1>

                        <div className={"EntitiesPage_Search"}>
                            <input
                                type={"text"}
                                className={"EntitiesPage_Input"}
                                placeholder={"Search..."}
                                onChange={this.onChange.bind(this)}
                            />
                        </div>
                    </div>

                    {entities.length > 0 ? (
                        <VirtualizedGrid
                            list={entities.filter((entity) => this.showEntity(entity))}
                            itemHeight={64}
                            itemsPerRow={18}
                            gap={5}
                            itemGap={5}
                            render={(entity) => (
                                <MiniCard
                                    key={entity.id}
                                    data={entity}
                                    icon={entityIcon(entity)}
                                    onClick={() => this.setSelectedItem(entity)}
                                />
                            )}
                        />
                    ) : undefined}
                </div>

                <div className={"EntitiesPage_Card"}>
                    <EntityCard entity={this.state.selected} info={this.state.selectedInfo} />
                </div>
            </div>
        );
    }
}

export default EntitiesPage;
