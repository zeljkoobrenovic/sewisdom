const fs = require('fs');

fs.readFile('quotes.json', 'utf8', function (err, contents) {
    var quotes = JSON.parse(contents);

    var text = "";

    quotes.forEach(function (q) {
        var quote = q.quote;
        quote = quote.replace(/<b>/g, "**");
        quote = quote.replace(/<\/b>/g, "**");

        quote = "*\"" + quote + "\"*";

        quote += "\n\n![" + q.citation.replace(/<.*?>/g, "") + "](images/" + q.year + "-" + q.number + ".jpg)";
        quote += "\n";

        text += quote + "\n\n{pagebreak}\n\n\n";
        console.log(quote);
    });

    fs.writeFile('../../../manuscript/chapter1.txt', text, function (err, contents) {
    });
});
