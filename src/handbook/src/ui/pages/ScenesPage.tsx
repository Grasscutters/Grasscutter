import React from "react";

import Card from "@widgets/Card";

import { SceneType } from "@backend/types";
import { getScenes } from "@backend/data";

import "@css/pages/ScenesPage.scss";

/**
 * Converts a scene type to a string.
 *
 * @param type The scene type.
 */
function sceneTypeToString(type: SceneType): string {
    switch (type) {
        default:
            return "Unknown";
        case SceneType.None:
            return "None";
        case SceneType.World:
            return "World";
        case SceneType.Activity:
            return "Activity";
        case SceneType.Dungeon:
            return "Dungeon";
        case SceneType.Room:
            return "Room";
        case SceneType.HomeRoom:
            return "Home Room";
        case SceneType.HomeWorld:
            return "Home World";
    }
}

class ScenesPage extends React.PureComponent {
    /**
     * Teleports the player to the specified scene.
     * @private
     */
    private async teleport(): Promise<void> {
        // TODO: Implement teleporting.
    }

    render() {
        return (
            <div className={"ScenesPage"}>
                <h1 className={"ScenesPage_Title"}>Scenes</h1>

                <div className={"ScenesPage_List"}>
                    {getScenes().map((command) => (
                        <Card
                            key={command.identifier}
                            title={command.identifier}
                            alternate={`ID: ${command.id} | ${sceneTypeToString(command.type)}`}
                            button={
                                <button className={"ScenesPage_Button"} onClick={this.teleport.bind(this)}>
                                    Teleport
                                </button>
                            }
                            rightOffset={13}
                            height={75}
                        />
                    ))}
                </div>
            </div>
        );
    }
}

export default ScenesPage;
