(ns clojask-examples.techmldataset
  (:require [tech.v3.dataset :as ds]
            [clojask.dataframe :as ck])
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(defn get-from
  [list keys]
  (map #(get list %) keys))

(defn local-date-formatter
  [date]
  (let [formatter (DateTimeFormatter/ofPattern "yyyy/MM/dd")]
    (.format date formatter)))

;; Content Below
(defn -main
  []
  ;; define a tech.ml.dataset
  (def df (ds/->dataset "resources/employees.csv"))
  
  ;; transform it into a Clojask dataset
  (def clojask-df (ck/dataframe (fn []
                                  (let [headers (keys (first (ds/mapseq-reader (ds/head df 1))))]
                                    (conj (map #(get-from % headers) (ds/mapseq-reader df)) headers)))))
  (ck/print-df clojask-df)
  (ck/set-formatter clojask-df "UpdateDate" local-date-formatter)
  ;; You may find that UpdateDate has type java.time.LocalDate.
  ;; Before grouping or joining or clojask-df, transform it to java.util.Date
  
  ;; transform the results of Clojask to tech.ml.dataset
  (def result (ck/compute clojask-df 8 nil))
  (println result)
  (def df2 (ds/->dataset (map zipmap (repeat (first result)) (rest result))))
  ;; df2's columns are all strings, you may convert them if needed
  
  ;; More details about tech.ml.dataset can be found at
  ;; https://github.com/techascent/tech.ml.dataset
  )