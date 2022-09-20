(ns clojask-examples.ordinary-join
  (:require [clojask.dataframe :as ck]))


;; Content Below
(defn -main
  []
  (def x (ck/dataframe "resources/employees.csv"))
  (def y (ck/dataframe "resources/employees-workleave.csv"))
  (ck/set-type x "Employee" "int")
  (ck/set-type y "Employee" "int")
  (def z (ck/inner-join x y ["Employee"] ["Employee"]))
  (ck/print-df z)
  (ck/compute z 8 "outputs/inner-join.csv" :select ["1_Employee" "1_Salary" "2_WorkLeave"])
  (ck/compute (ck/left-join x y ["Employee"] ["Employee"]) 8 "outputs/left-join.csv" :exception false)
  (ck/compute (ck/right-join x y ["Employee"] ["Employee"]) 8 "outputs/right-join.csv" :exception false))