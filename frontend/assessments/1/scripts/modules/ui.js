const graphContainer = document.getElementById('graph-container');
 const priceText = document.getElementById('current_price');
const percentText = document.getElementById('percent-change');
 const arrowIcon = document.getElementById('arrow');
const historyList = document.getElementById('history');

function drawGraphBar(price, isUp) {
    const bar = document.createElement('div');
        bar.classList.add('bar');

   let height = price;

    if (height > 450) {
         height = 450;
    }

   bar.style.height = `${height}px`;

     if (isUp) {
        bar.classList.add('up');
     } else {
         bar.classList.add('down');
    }

    graphContainer.appendChild(bar);

    if (graphContainer.children.length > 20) {
         graphContainer.removeChild(graphContainer.firstChild);
    }
}

function updateMainDisplay(price, percent, isUp) {
   let color;

    if (isUp) {
        color = '#2f9e44';
    } else {
         color = '#e03131';
    }

    if (priceText) {
        priceText.innerText = price;
            priceText.style.color = color;
    }

    if (arrowIcon) {
        if (isUp) {
                arrowIcon.innerHTML = '&#8593;';
        } else {
            arrowIcon.innerHTML = '&#8595;';
        }
            arrowIcon.style.color = color;
    }

    if (percentText) {
        percentText.innerText = `${percent}%`;
            percentText.style.color = '#555';
    }
}

function addHistoryCard(qty, type, price) {
    const emptyState = document.querySelector('.empty');

    if (emptyState) {
        emptyState.remove();
    }

    const card = document.createElement('div');
    card.classList.add('history-card');

    card.innerHTML = `
        <div class="card-details">
            <strong>${qty} Stocks @ ${price}</strong>
             <small>${getTimestamp()}</small>
        </div>
    <div class="card-action ${type.toLowerCase()}">
            ${type}
        </div>
    `;

    historyList.prepend(card);
}
