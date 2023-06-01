import React from "react";

import HomePage from "@pages/HomePage";
import CommandsPage from "@pages/CommandsPage";
import AvatarsPage from "@pages/AvatarsPage";
import ItemsPage from "@pages/ItemsPage";
import EntitiesPage from "@pages/EntitiesPage";
import ScenesPage from "@pages/ScenesPage";
import QuestsPage from "@pages/QuestsPage";

import type { Page } from "@backend/types";
import { addNavListener, removeNavListener } from "@backend/events";

import "@css/views/Content.scss";

interface IProps {
    initial?: Page | null;
}

interface IState {
    current: Page;
}

class Content extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            current: props.initial ?? "Home"
        };
    }

    /**
     * Navigates to the specified page.
     *
     * @param page The page to navigate to.
     * @private
     */
    private navigate(page: Page): void {
        this.setState({ current: page });
    }

    componentDidMount() {
        addNavListener(this.navigate.bind(this));
    }

    componentWillUnmount() {
        removeNavListener(this.navigate.bind(this));
    }

    render() {
        switch (this.state.current) {
            default:
                return undefined;
            case "Home":
                return <HomePage />;
            case "Commands":
                return <CommandsPage />;
            case "Avatars":
                return <AvatarsPage />;
            case "Items":
                return <ItemsPage />;
            case "Entities":
                return <EntitiesPage />;
            case "Scenes":
                return <ScenesPage />;
            case "Quests":
                return <QuestsPage />;
        }
    }
}

export default Content;
