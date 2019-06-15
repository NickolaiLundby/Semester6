export default {
    render(props) {
        return `${this.html(props)}
                ${this.css(props)}`;
    },
    html(p) {
        `<h2>${p.title}</h2>` + 
            `<h4>by ${p.author}</h4>` +
            `<div class="image-container"></div>` +
            `<button class="back">&lt</button>` +
            `<button class="forward">&gt</button>`
    },
    css(p) {
        return `<style>
        .kpu-photo-carousel {
            position: relative;
            width: 500px;
            height: 300px;
            display: flex;
            padding-top: 10px;
            flex-direction: column;
            border-color: black;
            border-width: 1px;
            border-style: solid;
        }
        .kpu-photo-carousel h2,
        h4 {
            margin-bottom: 0;
            margin-top: 0;
            margin-left: 10px;
        }       
        .kpu-photo-carousel .image-container {
            margin-top: 15px;
            width: 100%;
            flex: 1;
            background-color: black;
            background-size: contain;
            background-repeat: no-repeat;
            background-position: 50%;
        }
        .kpu-photo-carousel button {
            cursor: pointer;
            background: transparent;
            border: none;
            font-size: 48px;
            color: white;
            position: absolute;
            top: 50%;
        }
        .kpu-photo-carousel button.back {
            left: 10px;
        }
        .kpu-photo-carousel button.forward {
            right: 10px;
        }
        </style>`
    }
}