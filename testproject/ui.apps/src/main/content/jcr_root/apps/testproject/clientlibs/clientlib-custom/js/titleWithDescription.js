/**
 * Title with Description Component
 * Fetches and displays title with description data
 */

(function() {
    'use strict';

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initTitleWithDescription);
    } else {
        initTitleWithDescription();
    }

    /**
     * Initialize Title with Description
     */
    function initTitleWithDescription() {
        fetchTitleWithDescriptionData();
    }

    /**
     * Fetch data from GraphQL endpoint
     */
    function fetchTitleWithDescriptionData() {
        const container = document.querySelector('.title-description-container');
        if (!container) {
            console.warn('Title description container not found');
            return;
        }

        const url = "http://localhost:4502/graphql/execute.json/testproject/title-with-description";

        fetch(url)
            .then(function(response) {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(function(json) {
                if (json && json.data && json.data.titleWithDescriptionList && json.data.titleWithDescriptionList.items) {
                    renderTitleWithDescription(json.data.titleWithDescriptionList.items, container);
                } else {
                    console.error("Invalid response structure:", json);
                    container.innerHTML = '<p style="color: red;">Error loading data</p>';
                }
            })
            .catch(function(error) {
                console.error("Error fetching data:", error);
                container.innerHTML = '<p style="color: red;">Failed to load data</p>';
            });
    }

    /**
     * Render title with description items
     */
    function renderTitleWithDescription(items, container) {
        if (!items || items.length === 0) {
            container.innerHTML = '<p>No data available</p>';
            return;
        }

        // Clear container
        container.innerHTML = '';

        // Create elements for each item
        items.forEach(function(item, index) {
            const titleBlock = document.createElement('div');
            titleBlock.className = 'title-block';

            // Create title element
            const titleElement = document.createElement('h2');
            titleElement.innerHTML = item.title && item.title.html ? item.title.html : 'No Title';

            // Create description element
            const descElement = document.createElement('p');
            descElement.innerHTML = item.description && item.description.html ? item.description.html : 'No Description';

            // Append to block
            titleBlock.appendChild(titleElement);
            titleBlock.appendChild(descElement);

            // Append block to container
            container.appendChild(titleBlock);
        });
    }

})();