import React from "react";

interface IProps {

}

class Command extends React.PureComponent<IProps> {
    constructor(props: IProps) {
        super(props);
    }

    render() {
        return (
            <div>
                <a>command</a>
            </div>
        );
    }
}
