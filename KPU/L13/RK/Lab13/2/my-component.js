const template = document.createElement('template');
template.innerHTML = `
<div class="my-component">
    <h2>
        <slot name="greeting">Hello</slot>
        <slot name="firstName">Bob</slot>
    </h2>
</div>`;

export default class MyComponent extends HTMLElement {
    constructor() {
        super();
        this._shadowRoot = this.attachShadow({ 'mode': 'open' });
        this._shadowRoot.appendChild(template.content.cloneNode(true));
    }

}
if (!customElements.get('my-component')) {
    customElements.define('my-component', MyComponent);
}