(ns clojask-examples.ordinary-join
  (:require [clojask.dataframe :as ck]))
;; Content Below
(defn main
    []
    (def x (ck/dataframe "resources/employees.csv"))
    (def y (ck/dataframe "resources/employees-workleave.csv"))

    (ck/compute (ck/left-join x y ["Employee"] ["Employee"] 8) "resources/output.csv" :exception false)
    )