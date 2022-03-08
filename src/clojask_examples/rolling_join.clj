(ns clojask-examples.rolling-join)
;; Content Below
(defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    (clojask/set-type x "UpdateDate" "datetime")
    (clojask/set-type y "UpdateDate" "datetime"))

    (clojask/compute (clojask/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "resources/output.csv" :exception false)
    )