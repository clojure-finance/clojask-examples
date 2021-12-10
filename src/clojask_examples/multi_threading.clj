(ns clojask-examples.multi-threading
    (:require [clojask.dataframe :as clojask]
              [clojure.core.async :as async]))
  
  (defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    ;; create a thread for each operation
    (async/thread (clojask/set-type x "Salary" "double"))
    (async/thread (clojask/set-type y "WorkLeave" "double"))

    (clojask/left-join x y ["Employee"] ["Employee"] 4 "resources/output.csv" :exception false)
    )