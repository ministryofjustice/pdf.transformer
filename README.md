# pdf.transformer

A Clojure CLI program designed to rename PDF form fields

## Usage

Make sure that you have [Leiningen](https://github.com/technomancy/leiningen) installed
(on OS X: `brew install leiningen`).
Then

    git clone git@github.com:ministryofjustice/pdf.transformer.git
    cd pdf.tranformer
    lein run -i form.pdf -o out.pdf -j names.json

**form.pdf** is a PDF file with forms whose fields you wish to rename.
**out.pdf** is the name of the new file you wish to create
**names.json** is a file which contains the mappings of:

    { "old_name": "new_name", ... }


## License

Distributed under the BSD License
