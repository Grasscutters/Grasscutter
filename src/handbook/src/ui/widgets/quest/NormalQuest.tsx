import React from "react";

import { IoLocationSharp } from "react-icons/io5";

import type { Quest } from "@backend/types";

import "@css/widgets/quest/NormalQuest.scss";

interface IProps {
    quest: Quest;
    right?: boolean;
}

class NormalQuest extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        const { quest } = this.props;

        return (
            <div className={"NormalQuest"} datatype={this.props.right ? "right" : "left"}>
                <div className={"NormalQuest_Info"}>
                    <p className={"font-bold"}>{quest.description}</p>
                    <p>
                        ID: {quest.id} | Main: {quest.mainId}
                    </p>
                </div>

                <IoLocationSharp className={"NormalQuest_Icon"} />
            </div>
        );
    }
}

export default NormalQuest;
