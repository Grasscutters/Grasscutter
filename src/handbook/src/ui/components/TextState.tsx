import React from "react";

import emitter from "@backend/events";

interface IProps {
    initial: boolean;
    event: string;
    text1: string;
    text2: string;
}

interface IState {
    state: boolean;
}

class TextState extends React.Component<IProps, IState> {
    constructor(props: IProps) {
        super(props);

        this.state = {
            state: false
        };
    }

    /**
     * Updates the current state.
     * @private
     */
    private update(state: boolean): void {
        this.setState({ state });
    }

    componentDidMount(): void {
        emitter.on(this.props.event, this.update.bind(this));
    }

    componentWillUnmount(): void {
        emitter.off(this.props.event, this.update);
    }

    render() {
        return this.state.state ? this.props.text2 : this.props.text1;
    }
}

export default TextState;
