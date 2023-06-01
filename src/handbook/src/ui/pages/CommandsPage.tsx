import React from "react";

import Card from "@widgets/Card";

import { listCommands } from "@backend/data";

import "@css/pages/CommandsPage.scss";

class CommandsPage extends React.PureComponent {
    render() {
        return (
            <div className={"CommandsPage"}>
                <h1 className={"CommandsPage_Title"}>Commands</h1>

                <div className={"CommandsPage_List"}>
                    {listCommands().map((command) => (
                        <Card
                            key={command.name[0]}
                            title={command.name[0]}
                            alternate={
                                command.name.length == 1 ? undefined : `(aka /${command.name.slice(1).join(", /")})`
                            }
                            description={command.description}
                        />
                    ))}
                </div>
            </div>
        );
    }
}

export default CommandsPage;
