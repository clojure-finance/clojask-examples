(ns clojask-examples.ordinary-join)

(defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    (compute (clojask/left-join x y ["Employee"] ["Employee"] 8) "resources/output.csv" :exception false)
    )