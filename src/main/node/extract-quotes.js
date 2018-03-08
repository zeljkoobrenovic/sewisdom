const fs = require('fs');

var run = function (id, startYear, endYear) {
    fs.readFile('quotes.json', 'utf8', function (err, contents) {
        var quotes = JSON.parse(contents);

        var text = "";
        var prevYear = 0;
        var prevCover = "";
        var i = 0;

        quotes.filter(function (q) {
            return q.year >= startYear && q.year <= endYear;
        }).sort(function (a, b) {
            return (a.year * 100000 + a.number * 1000 + a.page) - (b.year * 100000 + b.number * 1000 + b.page);
        }).forEach(function (q) {
            i++;
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
            quote = quote.replace(/"/g, "'");
            quote = quote.replace(/\n/g, " ");

            quote = "  \n* * *\n\n| \"" + quote + "\" |\n";
            quote += "\n\n* * *\n\n| ***" + q.citation.replace(/<.*?>/g, "") + ".***[^foo" + i + "] |\n\n";
            quote += "[^foo" + i + "]: <" + q.doiLink + ">";

            quote += "\n";

            text += quote + " \n \n{pagebreak}\n\n\n";
            console.log(quote);
        });

        fs.writeFile('../../../manuscript/' + id + '.txt', text, function (err, contents) {
        });
    });
}

run("quotes", 1984, 2018);

run("quotes1", 1984, 1999);
run("quotes2", 2000, 2009);
run("quotes3", 2010, 2018);

