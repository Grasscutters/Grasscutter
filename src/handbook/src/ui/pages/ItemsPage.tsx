import React, { ChangeEvent } from "react";

import MiniCard from "@widgets/MiniCard";
import ItemCard from "@widgets/ItemCard";
import VirtualizedGrid from "@components/VirtualizedGrid";

import { ItemCategory } from "@backend/types";
import type { Item as ItemType, ItemInfo } from "@backend/types";
import { getItems, sortedItems } from "@backend/data";
import { fetchItemData, itemIcon } from "@app/utils";

import "@css/pages/ItemsPage.scss";

interface IState {
    filters: ItemCategory[];
    search: string;

    selected: ItemType | null;
    selectedInfo: ItemInfo | null;
}

class ItemsPage extends React.Component<{}, IState> {
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

    /**
     * Should the item be showed?
     *
     * @param item The item.
     * @private
     */
    private showItem(item: ItemType): boolean {
        // Check if the item has an icon.
        if (item.icon.length == 0) return false;
        // Check if the item is a TCG card.
        if (item.icon.includes("Gcg")) return false;

        return item.id > 0;
    }

    /**
     * Sets the selected item.
     *
     * @param item The item.
     * @private
     */
    private async setSelectedItem(item: ItemType): Promise<void> {
        let data: ItemInfo | null = null;
        try {
            data = await fetchItemData(item);
        } catch {}

        this.setState({
            selected: item,
            selectedInfo: data
        });
    }

    render() {
        const items = this.getItems();

        return (
            <div className={"ItemsPage"}>
                <div className={"ItemsPage_Content"}>
                    <div className={"ItemsPage_Header"}>
                        <h1 className={"ItemsPage_Title"}>Items</h1>

                        <div className={"ItemsPage_Search"}>
                            <input
                                type={"text"}
                                className={"ItemsPage_Input"}
                                placeholder={"Search..."}
                                onChange={this.onChange.bind(this)}
                            />
                        </div>
                    </div>

                    {items.length > 0 ? (
                        <VirtualizedGrid
                            list={items.filter((item) => this.showItem(item))}
                            itemHeight={64}
                            itemsPerRow={18}
                            gap={5}
                            itemGap={5}
                            render={(item) => (
                                <MiniCard
                                    key={item.id}
                                    data={item}
                                    icon={itemIcon(item)}
                                    onClick={() => this.setSelectedItem(item)}
                                />
                            )}
                        />
                    ) : undefined}
                </div>

                <div className={"ItemsPage_Card"}>
                    <ItemCard item={this.state.selected} info={this.state.selectedInfo} />
                </div>
            </div>
        );
    }
}

export default ItemsPage;
