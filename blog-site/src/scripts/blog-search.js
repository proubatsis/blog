class BlogSearchApi {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    getResults(searchTerm) {
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest();
            xhr.open('GET', `${this.baseUrl}/${searchTerm}`);
            xhr.onload = () => {
                if (xhr.status === 200) {
                    resolve(JSON.parse(xhr.responseText).results);
                } else {
                    reject();
                }
            };
            xhr.send();
        });
    }
}

class BlogSearch extends HTMLElement {
    constructor() {
        super();
        this.REFRESH_RATE_MS = 500;
        this.SEARCH_TERM_ATTR = 'search-term';
        this.EMPTY = 'empty';

        this.searchInput = document.createElement('input');
        this.searchInput.setAttribute('type', 'text');
        this.searchInput.setAttribute('placeholder', 'Search');
        this.appendChild(this.searchInput);

        this.searchResultsDiv = document.createElement('div');
        this.searchResultsDiv.className = 'search-results';
        this.searchResultsDiv.classList.add(this.EMPTY);
        this.appendChild(this.searchResultsDiv);

        this.apiClient = new BlogSearchApi(this.getAttribute('src'));

        setInterval(async () => {
            this.refreshResults();
        }, this.REFRESH_RATE_MS);
    }

    async refreshResults() {
        const lastSearchTerm = this.getAttribute(this.SEARCH_TERM_ATTR);
        const searchTerm = this.searchInput.value; 

        if (searchTerm === '' || searchTerm === null) {
            this.showResults([]);
        } else if (searchTerm !== lastSearchTerm) {
            const results = await this.apiClient.getResults(searchTerm);
            this.setAttribute(this.SEARCH_TERM_ATTR, searchTerm);
            this.showResults(results);
        }
    }

    showResults(results) {
        this.searchResultsDiv.innerHTML = '';

        if (results.length === 0 && !this.searchResultsDiv.classList.contains(this.EMPTY)) {
            this.searchResultsDiv.classList.add(this.EMPTY);
        } else if (results.length > 0 && this.searchResultsDiv.classList.contains(this.EMPTY)) {
            this.searchResultsDiv.classList.remove(this.EMPTY);
        }

        results
            .map(r => this._renderResult(r))
            .forEach(e => this.searchResultsDiv.appendChild(e));
    }

    _renderResult(result) {
        const element = document.createElement('div');
        element.className = 'search-result';
        element.innerHTML = `<p>${result.title}</p>`;
        return element;
    }
}

window.customElements.define('blog-search', BlogSearch);
