(ns clojask-examples.groupby-aggregate
  (:require [clojask.dataframe :as ck]
            [clojask.api.aggregate :as agg]
            [clojask.api.gb-aggregate :as gb-agg]))

(defn -main
  []
  ;; groupby aggregate
  (def df (ck/dataframe "resources/employees.csv"))
  (ck/set-type df "UpdateDate" "date:yyyy/MM/dd")
  (ck/set-type df "Salary" "int")
  (ck/group-by df "Department")
  (ck/aggregate df gb-agg/max "UpdateDate")
  (ck/aggregate df #(reduce + %) "Salary") ;; can aggregate on multiple columns in a group
  (ck/compute df 8 "outputs/gb-agg.csv")

  ;; direct aggregate
  (def df (ck/dataframe "resources/employees.csv"))
  (ck/set-type df "UpdateDate" "date:yyyy/MM/dd")
  (ck/set-type df "Salary" "int")
  (ck/aggregate df agg/max "UpdateDate")
  (ck/aggregate df agg/min "UpdateDate") ;; can aggregate on multiple columns
  (ck/compute df 8 "outputs/agg.csv")
  )