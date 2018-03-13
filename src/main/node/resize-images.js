const fs = require('fs');
const sharp = require('sharp');

var dirSrc = '../../../manuscript/images-high-quality/';
var dirDest = '../../../manuscript/images-medium-quality/';

fs.readdir(dirSrc, function(err, files) {
    files.forEach(function (file) {
        sharp(dirSrc + file)
            .resize(undefined, 800)
            .toFile(dirDest + file, function (err) {
                if (err) console.log(err);
            });
    });
});



