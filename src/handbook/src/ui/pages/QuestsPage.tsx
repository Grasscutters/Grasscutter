import React from "react";

import Tree, { RawNodeDatum } from "react-d3-tree";

import PrimaryQuest from "@widgets/quest/PrimaryQuest";

import "@css/pages/QuestsPage.scss";

const defaultTree: RawNodeDatum = {
    name: "No Quest Selected",
    attributes: {
        questId: -1
    },
    children: []
};

interface IState {
    tree: RawNodeDatum | null;
}

class QuestsPage extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            tree: null
        };
    }

    render() {
        return (
            <div className={"QuestsPage"}>
                <div className={"QuestsPage_Selector"}>
                    <PrimaryQuest
                        quest={{
                            id: 351,
                            title: "Across the Sea"
                        }}
                    />
                </div>

                <div className={"QuestsPage_Tree"}>
                    <Tree data={this.state.tree ?? defaultTree} />
                </div>
            </div>
        );
    }
}

export default QuestsPage;
