function getRandomPrice() {
    const value = Math.random() * 500;
     return value.toFixed(2);
}

function getTimestamp() {
    const now = new Date();
    return now.toUTCString();
}

function getPercentageChange(oldPrice, newPrice) {
    if (oldPrice === 0) {
       return "0.00";
    }

    const change = ((newPrice - oldPrice) / oldPrice) * 100;
            return change.toFixed(2);
}
