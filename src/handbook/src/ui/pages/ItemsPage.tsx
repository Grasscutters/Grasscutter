import React, { ChangeEvent } from "react";

import Item from "@widgets/Item";
import VirtualizedGrid from "@components/VirtualizedGrid";

import { ItemCategory } from "@backend/types";
import type { Item as ItemType } from "@backend/types";
import { getItems, sortedItems } from "@backend/data";

import "@css/pages/ItemsPage.scss";

interface IState {
    filters: ItemCategory[];
    search: string;
}

class ItemsPage extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            filters: [],
            search: ""
        };
    }

    /**
     * Gets the items to render.
     * @private
     */
    private getItems(): ItemType[] {
        let items: ItemType[] = [];

        // Add items based on filters.
        const filters = this.state.filters;
        if (filters.length == 0) {
            items = getItems();
        } else {
            for (const filter of filters) {
                // Add items from the category.
                items = items.concat(sortedItems[filter]);
                // Remove duplicate items.
                items = items.filter((item, index) => {
                    return items.indexOf(item) == index;
                });
            }
        }

        // Filter out items that don't match the search.
        const search = this.state.search.toLowerCase();
        if (search != "") {
            items = items.filter((item) => {
                return item.name.toLowerCase().includes(search);
            });
        }

        return items;
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

    render() {
        const items = this.getItems();

        return (
            <div className={"ItemsPage"}>
                <div className={"ItemsPage_Header"}>
                    <h1 className={"ItemsPage_Title"}>Items</h1>

                    <div className={"ItemsPage_Search"}>
                        <input type={"text"}
                               className={"ItemsPage_Input"}
                               placeholder={"Search..."}
                               onChange={this.onChange.bind(this)}
                        />
                    </div>
                </div>

                {
                    items.length > 0 ? (
                        <VirtualizedGrid
                            list={items} itemHeight={64}
                            itemsPerRow={20} gap={5} itemGap={5}
                            render={(item) => (
                                <Item key={item.id} data={item} />
                            )}
                        />
                    ) : undefined
                }
            </div>
        );
    }
}

export default ItemsPage;
