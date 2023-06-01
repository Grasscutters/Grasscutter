import React from "react";

import emitter from "@backend/events";
import { targetPlayer, address, port, setServerDetails, url, setTargetPlayer } from "@backend/server";
import { getWindowDetails } from "@app/utils";

import "@css/widgets/ServerSettings.scss";

interface IState {
    webview: boolean;

    address: string;
    port: number;
}

class ServerSettings extends React.Component<{}, IState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            webview: false,
            address: address,
            port: Number(port)
        };
    }

    componentDidMount() {
        window.addEventListener("keyup", this.escapeListener.bind(this));
    }

    componentWillUnmount() {
        window.removeEventListener("keyup", this.escapeListener.bind(this));
        window.removeEventListener("message", this.handleAuthentication.bind(this));
    }

    /**
     * Invoked when the escape key is pressed.
     *
     * @param e The keyboard event.
     * @private
     */
    private escapeListener(e: KeyboardEvent): void {
        if (e.key === "Escape") {
            // Hide the overlay.
            emitter.emit("overlay", "None");
        }
    }

    /**
     * Invoked when the component tries to authenticate.
     * @private
     */
    private authenticate(): void {
        setServerDetails(this.state.address, this.state.port).then(() => {
            this.setState({ webview: true });
        });

        // Add the event listener for authentication.
        window.addEventListener("message", this.handleAuthentication.bind(this));
    }

    /**
     * Finishes the authentication process.
     *
     * @param e The message event.
     * @private
     */
    private handleAuthentication(e: MessageEvent): void {
        const data = e.data; // The data sent from the server.
        if (data == null) return; // If the data is null, return.

        // Check if the data is an object.
        if (typeof data != "object") return;
        // Get the data type.
        const type = data["type"] ?? null;
        if (type != "handbook-auth") return;

        // Get the data.
        const uid = data["uid"] ?? null;
        const token = data["token"] ?? null;

        // Hide the overlay.
        emitter.emit("overlay", "None");
        // Set the token and user ID.
        setTargetPlayer(Number(uid), token);
    }

    /**
     * Invoked when the save button is clicked.
     * @private
     */
    private save(): void {
        // Hide the overlay.
        emitter.emit("overlay", "None");

        // Save the server settings.
        setServerDetails(this.state.address, this.state.port.toString());
    }

    render() {
        const { disable } = getWindowDetails();

        return (
            <div className={"ServerSettings"}>
                {this.state.webview ? (
                    <iframe
                        className={"ServerSettings_Frame"}
                        src={`${url()}/handbook/authenticate?uid=${targetPlayer}`}
                    />
                ) : (
                    <>
                        <div className={"ServerSettings_Content ServerSettings_Top"}>
                            <h1 className={"ServerSettings_Title"}>Server Settings</h1>

                            <div
                                className={"ServerSettings_Details"}
                                style={{
                                    opacity: disable ? 0.5 : 1,
                                    cursor: disable ? "not-allowed" : "default",
                                    userSelect: disable ? "none" : "auto"
                                }}
                            >
                                <div>
                                    <p>Address:</p>
                                    <input
                                        type={"text"}
                                        value={this.state.address}
                                        onChange={(e) => {
                                            const target = e.target as HTMLInputElement;
                                            const value = target.value;

                                            this.setState({ address: value });
                                        }}
                                        disabled={disable}
                                        style={{
                                            cursor: disable ? "not-allowed" : "text",
                                            userSelect: disable ? "none" : "auto"
                                        }}
                                    />
                                </div>

                                <div>
                                    <p>Port:</p>
                                    <input
                                        type={"text"}
                                        value={this.state.port == 0 ? "" : this.state.port}
                                        onChange={(e) => {
                                            const target = e.target as HTMLInputElement;
                                            const value = target.value;

                                            if (isNaN(Number(value)) || value.length > 5) {
                                                return;
                                            }

                                            this.setState({ port: Number(value) });
                                        }}
                                        disabled={disable}
                                        style={{
                                            cursor: disable ? "not-allowed" : "text",
                                            userSelect: disable ? "none" : "auto"
                                        }}
                                    />
                                </div>
                            </div>

                            <button className={"ServerSettings_Authenticate"} onClick={this.authenticate.bind(this)}>
                                Authenticate
                            </button>
                        </div>

                        <div className={"ServerSettings_Content"}>
                            <button className={"ServerSettings_Save"} onClick={this.save.bind(this)}>
                                Save
                            </button>
                        </div>
                    </>
                )}
            </div>
        );
    }
}

export default ServerSettings;
