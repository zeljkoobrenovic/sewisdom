const fs = require('fs');
const lwip = require('lwip');

var dirSrc = '../../../manuscript/images-high-quality/';
var dirDest = '../../../manuscript/images-medium-quality/';

fs.readdir(dirSrc, function (err, files) {
    files.forEach(function (file) {
        var srcImage = dirSrc + file;
        var destImage = dirDest + file;
        lwip.open(srcImage, function (err, image) {
            if (err) return console.log(err);
            image.batch()
                .scale(0.5)
                .writeFile(destImage, function (err) {
                    if (err) console.log(err);
                });

        });
    });
});



