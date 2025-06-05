import React, { useEffect, useState } from "react";

const TitleWithDescription = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch("http://localhost:4502/graphql/execute.json/testproject/title-with-description")
            .then(response => response.json())
            .then(json => {
                setData(json.data.titleWithDescriptionList.items);
            })
            .catch(error => console.error("Error fetching data:", error));
    }, []);

    return (
        <div className="title-description-container">
            {data.map((item, index) => (
                <div key={index} className="title-block">
                    <h2 dangerouslySetInnerHTML={{ __html: item.title.html }} />
                    <p dangerouslySetInnerHTML={{ __html: item.description.html }} />
                </div>
            ))}
        </div>
    );
};

export default TitleWithDescription;