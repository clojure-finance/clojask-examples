(ns clojask-examples.multi-threading
    (:require [clojask.dataframe :as ck]
              [clojure.core.async :as async]))
  ;; Content Below
  (defn main
    []
    (def x (ck/dataframe "resources/employees.csv"))
    (def y (ck/dataframe "resources/employees-workleave.csv"))

    ;; create a thread for each operation
    (async/thread (ck/set-type x "Salary" "double"))
    (async/thread (ck/set-type y "WorkLeave" "double"))

    (ck/compute (ck/left-join x y ["Employee"] ["Employee"]) 8 "resources/output.csv" :exception false)
    )