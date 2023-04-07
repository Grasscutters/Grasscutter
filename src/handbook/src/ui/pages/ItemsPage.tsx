import React from "react";

import { getItems } from "@backend/data";

import "@css/pages/ItemsPage.scss";

class ItemsPage extends React.PureComponent {
    render() {
        return (
            <div className={"ItemsPage"}>
                <h1 className={"ItemsPage_Title"}>Items</h1>

                <div className={"ItemsPage_List"}>
                    {getItems().map((item) => (
                        <p key={item.id}>{item.name}</p>
                    ))}
                </div>
            </div>
        );
    }
}

export default ItemsPage;
