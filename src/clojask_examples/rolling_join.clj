(ns clojask-examples.rolling-join 
  (:require [clojask.dataframe :as ck]))

;; Content Below
(defn main
    []
    (def x (ck/dataframe "resources/employees.csv"))
    (def y (ck/dataframe "resources/employees-workleave.csv"))

    (ck/set-type x "UpdateDate" "datetime")
    (ck/set-type y "UpdateDate" "datetime")

    (ck/compute (ck/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "resources/output.csv" :exception false)
    )