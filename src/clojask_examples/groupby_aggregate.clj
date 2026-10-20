(ns clojask-examples.groupby-aggregate
  (:require [clojask.dataframe :as ck]
            [clojask.api.aggregate :as agg]
            [clojask.api.gb-aggregate :as gb-agg]))

;; Content Below
(defn -main
  []
  ;; groupby aggregate
  (def df (ck/dataframe "resources/employees.csv"))
  (ck/set-type df "UpdateDate" "date:yyyy/MM/dd")
  (ck/set-type df "Salary" "int")
  (ck/group-by df "Department")
  (ck/aggregate df gb-agg/max "UpdateDate")
  ;; can aggregate on multiple columns in a group; the last argument names the
  ;; output column (the default name comes from the function, which for an
  ;; anonymous function is something like _main$fn__2093(Salary))
  (ck/aggregate df #(reduce + %) "Salary" "sum(Salary)")
  (ck/compute df 8 "outputs/gb-agg.csv")

  ;; direct aggregate
  (def df (ck/dataframe "resources/employees.csv"))
  (ck/set-type df "UpdateDate" "date:yyyy/MM/dd")
  (ck/set-type df "Salary" "int")
  (ck/aggregate df agg/max "UpdateDate")
  (ck/aggregate df agg/min "UpdateDate") ;; can aggregate on multiple columns
  (ck/compute df 8 "outputs/agg.csv"))