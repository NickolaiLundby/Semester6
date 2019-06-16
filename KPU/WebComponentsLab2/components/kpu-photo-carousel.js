import Template from './template.js'

class PhotoCarousel extends HTMLElement {
    connectedCallback() {
        this._photoIndex = 0;
        this._photos = this.getAttribute('photos').split(',');
        this.innerHTML = Template.render({
            title: this.getAttribute('title'),
            author: this.getAttribute('author')
        });

        this.showPhoto();

        this.querySelector('button.back').addEventListener('click', event =>
            this.onBackButtonClick(event));
        this.querySelector('button.forward').addEventListener('click', event =>
            this.onForwardButtonClick(event));
    }

    showPhoto() {
        this.querySelector('.image-container').style.backgroundImage = 'url(' +
            this._photos[this._photoIndex] + ')'
    }

    /**
     * handler for when user clicks the back button
     * @param event
     */
    onBackButtonClick(event) {
        this._photoIndex--;
        if (this._photoIndex < 0) {
            this._photoIndex = this._photos.length - 1;
        }
        this.showPhoto();
    }
    
    /**
     * handler for when user clicks the forward button
     * @param event
     */
    onForwardButtonClick(event) {
        this._photoIndex++;
        if (this._photoIndex >= this._photos.length) {
            this._photoIndex = 0;
        }
        this.showPhoto();
    }
}
if (!customElements.get('components/kpu-photo-carousel')) {
    customElements.define('components/kpu-photo-carousel', PhotoCarousel);
}