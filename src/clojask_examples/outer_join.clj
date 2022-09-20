(ns clojask-examples.outer-join
  (:require [clojask.dataframe :as ck]))

;; Content Below
(defn -main
  []
  (def x (ck/dataframe "resources/employees.csv"))
  (def y (ck/dataframe "resources/employees-workleave.csv"))
  (ck/set-type x "Employee" "int")
  (ck/set-type y "Employee" "int")
  (def z (ck/outer-join x y ["Employee"] ["Employee"]))
  (ck/print-df z)
  (ck/compute z 8 "outputs/outer-join.csv" :exclude ["2_Employee" "2_EmployeeName"]))