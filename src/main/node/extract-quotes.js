const fs = require('fs');

fs.readFile('quotes.json', 'utf8', function (err, contents) {
    var quotes = JSON.parse(contents);

    var text = "";
    var prevYear = 0;
    var prevCover = "";

    for (var i = quotes.length - 1; i >= 0; i--) {
        var q = quotes[i];
        var quote = q.quote;

        var year = q.year;
        var number = q.number;

        if (year !== prevYear) {
            text += "# " + year + "\n{pagebreak}\n\n\n";
            prevYear = year;
        }

        var cover = year + "-" + number;
        if (prevCover !== cover) {
            text += "\n\n\n\n![](images/" + cover + ".jpg)\n\n\n\n\n{pagebreak}\n\n";
            prevCover = cover;
        }


        quote = quote.replace(/<b>/g, "**");
        quote = quote.replace(/<\/b>/g, "**");
        quote = quote.replace(/\[/g, "");
        quote = quote.replace(/\]/g, "");

        quote = "  \n* * *\n\n\"" + quote + "\"";
        quote += "  \n\n* * *\n\n***" + q.citation.replace(/<.*?>/g, "") + "***[^foo" + i + "]\n\n";
        quote += "[^foo" + i + "]: " + q.doiLink;

        quote += "\n";

        text += quote + " \n \n{pagebreak}\n\n\n";
        console.log(quote);
    }

    fs.writeFile('../../../manuscript/chapter1.txt', text, function (err, contents) {
    });
});
