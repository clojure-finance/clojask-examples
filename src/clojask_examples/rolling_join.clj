(ns clojask-examples.rolling-join)

(defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    (clojask/compute (clojask/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "resources/output.csv" :exception false)
    )