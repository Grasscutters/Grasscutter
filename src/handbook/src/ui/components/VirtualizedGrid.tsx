import React from "react";

import { List as _List, ListProps, ListRowProps } from "react-virtualized/dist/es/List";
import { AutoSizer as _AutoSizer, AutoSizerProps } from "react-virtualized/dist/es/AutoSizer";

const List = _List as unknown as React.FC<ListProps>;
const AutoSizer = _AutoSizer as unknown as React.FC<AutoSizerProps>;

import "@css/components/VirtualizedGrid.scss";

interface IProps<T> {
    list: T[];
    render: (item: T) => React.ReactNode;

    itemHeight: number;
    itemsPerRow?: number;

    gap?: number;
    itemGap?: number;
}

interface IState {
    scrollTop: number;
    itemsPerRow: number;
}

class VirtualizedGrid<T> extends React.Component<IProps<T>, IState> {
    constructor(props: IProps<T>) {
        super(props);

        this.state = {
            scrollTop: 0,
            itemsPerRow: 10
        };
    }

    /**
     * Renders a row of items.
     */
    private rowRender(props: ListRowProps): React.ReactNode {
        const items: React.ReactNode[] = [];

        // Calculate the items to render.
        const perRow = this.state.itemsPerRow ?? 10;
        for (let i = 0; i < perRow; i++) {
            const itemIndex = props.index * perRow + i;
            if (itemIndex < this.props.list.length) {
                items.push(this.props.render(this.props.list[itemIndex]));
            }
        }

        return (
            <div
                key={props.key}
                style={{
                    ...props.style,
                    gap: this.props.itemGap ?? 0
                }}
                className={"GridRow"}
            >
                {items.map((item, index) => (
                    <div key={index}>{item}</div>
                ))}
                <div style={{ height: this.props.gap ?? 0 }} />
            </div>
        );
    }

    componentDidMount() {
        this.setState({
            itemsPerRow: Math.floor((window.innerWidth - 650) / (this.props.itemHeight + (this.props.gap ?? 0)))
        });

        window.addEventListener("resize", () => {
            this.setState({
                itemsPerRow: Math.floor((window.innerWidth - 650) / (this.props.itemHeight + (this.props.gap ?? 0)))
            });
        });
    }

    render() {
        const { list, itemHeight } = this.props;

        return (
            <AutoSizer>
                {({ height, width }) => (
                    <List
                        height={height - 150}
                        width={width}
                        rowHeight={itemHeight + (this.props.gap ?? 0)}
                        rowCount={Math.ceil(list.length / (this.state.itemsPerRow ?? 10))}
                        rowRenderer={this.rowRender.bind(this)}
                        scrollTop={this.state.scrollTop}
                        onScroll={(e) => this.setState({ scrollTop: e.scrollTop })}
                    />
                )}
            </AutoSizer>
        );
    }
}

export default VirtualizedGrid;
