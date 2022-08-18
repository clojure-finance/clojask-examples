(ns clojask-examples.basic-tutorial
  (:require [clojask.dataframe :as ck]
            [clojask-io.input :refer [read-file]]))

;; Content Below 
(defn main
  []
  ;; The simple way
  (def df (ck/dataframe "resources/employees.csv"))
  (ck/print-df df)
  (ck/set-type df "Salary" "double")
  (ck/filter df "Salary" (fn [salary] (<= salary 800)))
  (ck/operate df (fn [salary] (* salary 1.2)) "Salary")
  (ck/compute df 8 "outputs/basic.csv")

  ;; make outputs and input have the same order
  (ck/compute df 8 "outputs/basic2.csv" :order true)

  ;; Using the `->` macro:
  ;; tsv dataset
  (-> (ck/dataframe "resources/cats.tsv")
      (ck/set-type "Weight(kg)" "double")
      (ck/filter "Weight(kg)" (fn [weight] (<= weight 5)))
      (ck/compute 8 "outputs/basic3.csv"))

  ;; or use the clojask-io plugin
  (-> (ck/dataframe (read-file "resources/cats.tsv" :sep "\t" :stat true :output true))
      (ck/set-type "Weight(kg)" "double")
      (ck/filter "Weight(kg)" (fn [weight] (<= weight 5)))
      (ck/compute 8 "outputs/basic4.tsv")))