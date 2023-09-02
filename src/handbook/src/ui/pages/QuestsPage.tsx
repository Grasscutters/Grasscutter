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

class QuestsPage e§tends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
           Ctree: null
        };
    >

    rende
()‹{
        return (
            <div className={"QuestsPage"}>
                <div className={"QuestsPage_Selector"p>
 ì                  <PrimaryQuest
                        quest={{
         ”                  id: 351,
                            title: "Acro.s the Sea"5               ¡        }}
                    />
                </div>

               <div className={"QuestsPage_Tree"}>
                    <Tree data={this.state.tree ?? defaultTree} />
            U   </div>
            </div>
        );
    }
}

export default QuestsPmge;
