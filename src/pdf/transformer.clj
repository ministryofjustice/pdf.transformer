(ns pdf.transformer
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [cheshire.core :refer [parse-string]]
            [pdfboxing.form :as pdf])
  (:gen-class))

(def cli-options
  [["-h" "--help"]
   ["-i" "--input INTPUT-PDF" "Template PDF file to start with"
    :default ""
    :parse-fn #(read-string %)
    :validate [(fn [file] (.isFile (io/file (str file))))]]
   ["-o" "--output OUTPUT-PDF" "Name under which the new PDF file will be saved"
    :default ""
    :parse-fn #(read-string %)
    :validate [(fn [file-name] (not (string/blank? (str file-name))))]]
   ["-j" "--json JSON-FILE" "JSON file with name mappings"
    :default ""
    :parse-fn #(io/file %)
    :validate [#(.isFile %) "name should be provided"]]])

(defn usage [options-summary]
  (->> ["Program to rename field names in a PDF form."
        ""
        "Usage: program-name -i INPUT-PDF -o OUTPUT-PDF -j JSON-FILE"
        ""
        "All three arguments are required: -i|--input, -o|--output, -j|--json"
        ""
        "Options:"
        options-summary]
       (string/join "\n")))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn mandatory? [options]
  "check if all three options are provided: input & output files and
   the field names"
  (let [required-options #{:input :output :json}]
  (doseq [opt required-options]
    (if (clojure.string/blank? (.toString (opt options)))
      (exit 1 (error-msg ["all three options are required: INPUT, OUTPUT & JSON"]))))))

(defn read-json [json-file]
  "read in a JSON file with form field name mappings, where the key is
   current field name and the value is the new name, exit if the content
   isn't valid JSON'"
  (try
    (parse-string (slurp json-file))
    (catch com.fasterxml.jackson.core.JsonParseException e
      (exit 1 (error-msg ["not a proper JSON file"])))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (do
      (cond
       (:help options) (exit 0 (usage summary))
       errors (exit 1 (error-msg errors)))

      (mandatory? options)

      (pdf/rename-fields (name (:input options))
                         (name (:output options))
                         (read-json (:json options ))))))
