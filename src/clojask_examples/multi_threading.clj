(ns clojask-examples.multi-threading
    (:require [clojask.dataframe :as cj]
              [clojure.core.async :as async]))
  ;; Content Below
  (defn main
    []
    (def x (cj/dataframe "resources/employees.csv"))
    (def y (cj/dataframe "resources/employees-workleave.csv"))

    ;; create a thread for each operation
    (async/thread (cj/set-type x "Salary" "double"))
    (async/thread (cj/set-type y "WorkLeave" "double"))

    (compute (cj/left-join x y ["Employee"] ["Employee"]) 8 "resources/output.csv" :exception false)
    )