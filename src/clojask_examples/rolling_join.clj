(ns clojask-examples.rolling-join 
  (:require [clojask.dataframe :as ck]))

;; Content Below
(defn main
    []
    (def x (ck/dataframe "resources/employees.csv"))
    (def y (ck/dataframe "resources/employees-workleave.csv"))

    (ck/set-type x "UpdateDate" "date:YYYY/mm/dd")
    (ck/set-type y "UpdateDate" "date:YYYY/mm/dd")

    (ck/compute (ck/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "outputs/rolling.csv" :exception false)
    )