(defproject pdf.transformer "0.1.0"
  :description "PDF from field rename CLI tool"
  :url "https://github.com/ministryofjustice/pdf.tranformer"
  :license {:name "BSD License"
            :url "http://en.wikipedia.org/wiki/BSD_licenses"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.3.1"]
                 [cheshire "5.3.1"]
                 [pdfboxing "0.1.3"]]
  :main pdf.transformer
  :aot :all)
