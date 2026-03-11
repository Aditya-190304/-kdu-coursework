const fs = require('fs');

function readfile(filepath) {
    const data = fs.readFileSync(filepath, "utf-8");
    return JSON.parse(data);
}

module.exports = { readfile };
