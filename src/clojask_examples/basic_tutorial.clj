(ns clojask-examples.basic-tutorial
    (:require [clojask.dataframe :as clojask]
              [clojure.core.async :as async]))
  
  (defn main
    []
    ;; The simple way
    (def df (clojask/dataframe "resources/employees.csv"))
    (clojask/print-df df)
    (clojask/set-type df "Salary" "double")
    (clojask/filter df "Salary" (fn [salary] (<= salary 800)))
    (clojask/operate df (fn [salary] (* salary 1.2)) "Salary")
    (clojask/compute df 8 "resources/output.csv" :exception true)

    ;; Using the `->` macro:
    (-> (clojask/dataframe "resources/employees.csv")
        (clojask/set-type "Salary" "double")
        (clojask/filter "Salary" (fn [salary] (<= salary 800)))
        (clojask/operate (fn [salary] (* salary 1.2)) "Salary")
        (clojask/compute 8 "resources/output.csv" :exception true))

    ;; import csv files with no column name header
    (def df (clojask/dataframe "resources/employees.csv" :have-col false))
    (clojask/print-df df)
    )