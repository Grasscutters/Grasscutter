import React from "react";

import Card from "@widgets/Card";

import { SceneType } from "@backend/types";
import { getScenes } from "@backend/data";
import { connected, teleportTo } from "@backend/server";
import { action } from "@backend/commands";
import { copyToClipboard } from "@app/utils";

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
    private async teleport(scene: number): Promise<void> {
        if (connected) {
            await teleportTo(scene);
        } else {
            await copyToClipboard(action.teleport(scene));
        }
    }

    render() {
        return (
            <div className={"ScenesPage"}>
                <h1 className={"ScenesPage_Title"}>Scenes</h1>

                <div className={"ScenesPage_List"}>
                    {getScenes().map((scene) => (
                        <Card
                            key={scene.id}
                            title={scene.identifier}
                            alternate={`ID: ${scene.id} | ${sceneTypeToString(scene.type)}`}
                            button={
                                <button className={"ScenesPage_Button"} onClick={() => this.teleport(scene.id)}>
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
