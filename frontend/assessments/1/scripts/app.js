let currentPrice = 250.00;
let previousPrice = 250.00;

const btnBuy = document.getElementById('buy');
    const btnSell = document.getElementById('sell');
const inputQty = document.getElementById('qty');

function tick() {
     const newPrice = parseFloat(getRandomPrice());
    const percent = getPercentageChange(previousPrice, newPrice);

    let isUp;
    if (newPrice >= previousPrice) {
    isUp = true;
    } else {
    isUp = false;
    }

    drawGraphBar(newPrice, isUp);
        updateMainDisplay(newPrice, percent, isUp);

    previousPrice = currentPrice;
currentPrice = newPrice;
}

btnBuy.addEventListener('click', function () {
        const qty = inputQty.value;

    if (qty > 0) {
    addHistoryCard(qty, 'BUY', currentPrice);
        inputQty.value = '';
    } else {
            alert("Please enter a valid quantity!");
    }
});

btnSell.addEventListener('click', function () {
    const qty = inputQty.value;

    if (qty > 0) {
        addHistoryCard(qty, 'SELL', currentPrice);
    inputQty.value = '';
    } else {
          alert("Please enter a valid quantity!");
    }
});

setInterval(tick, 2000);
tick();
