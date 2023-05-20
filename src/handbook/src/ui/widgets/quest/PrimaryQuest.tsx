import React from "react";

import { GiSupersonicArrow } from "react-icons/gi";

import Collapsible from "react-collapsible";
import NormalQuest from "@widgets/quest/NormalQuest";

import type { MainQuest } from "@backend/types";
import { listSubQuestsFor } from "@backend/data";

import "@css/widgets/quest/PrimaryQuest.scss";

interface IProps {
    quest: MainQuest;
}

function Trigger(props: IProps): React.ReactElement {
    return (
        <div className={"Trigger"}>
            <GiSupersonicArrow className={"Trigger_Icon"} />
            <div className={"Trigger_Info"}>
                <p className={"font-bold"}>{props.quest.title}</p>
                <p>ID: {props.quest.id}</p>
            </div>
        </div>
    );
}

class PrimaryQuest extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        return (
            <Collapsible
                className={"PrimaryQuest"}
                openedClassName={"PrimaryQuest"}
                trigger={<Trigger quest={this.props.quest} />}
                transitionTime={50}
            >
                <div className={"PrimaryQuest_List"}>
                    {listSubQuestsFor(this.props.quest).map((quest) => (
                        <NormalQuest key={quest.id} quest={quest} right />
                    ))}
                </div>
            </Collapsible>
        );
    }
}

export default PrimaryQuest;
