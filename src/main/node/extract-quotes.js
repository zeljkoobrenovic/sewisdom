const fs = require('fs');

fs.readFile('quotes.json', 'utf8', function (err, contents) {
    var quotes = JSON.parse(contents);

    var text = "";

    quotes.forEach(function (q) {
        var quote = q.quote;
        quote = quote.replace(/<b>/g, "**");
        quote = quote.replace(/<\/b>/g, "**");

        quote = "*\"" + quote + "\"*";

        quote += "\n\n" + q.citation.replace(/<.*?>/g, "") + "\n\n\n";

        text += quote + "\n\n";
        console.log(quote);
    });

    fs.writeFile('chapter1.txt', text, function (err, contents) {
    });
});
