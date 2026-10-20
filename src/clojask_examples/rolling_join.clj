(ns clojask-examples.rolling-join
  (:require [clojask.dataframe :as ck]))

(defn within-days
  "A :limit for rolling joins: join two rows only when their roll values
   (here dates) are at most n days apart."
  [n]
  (fn [a b]
    (<= (Math/abs (- (.getTime a) (.getTime b)))
        (* n 24 60 60 1000))))

;; Content Below
(defn -main
  []
  (def x (ck/dataframe "resources/employees.csv"))
  (def y (ck/dataframe "resources/employees-workleave.csv"))

  (ck/set-type x "UpdateDate" "date:yyyy/MM/dd")
  (ck/set-type y "UpdateDate" "date:yyyy/MM/dd")

  ;; forward: each row of x gets the row of y with the same Employee and
  ;; the latest UpdateDate that is not after x's
  (ck/compute (ck/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "outputs/rolling-forward.csv")

  ;; backward: the earliest UpdateDate that is not before x's
  (ck/compute (ck/rolling-join-backward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "outputs/rolling-backward.csv")

  ;; threshold: :limit is called on the two roll values and skips the join
  ;; when it returns false; here rows more than 7 days apart stay unjoined
  (ck/compute (ck/rolling-join-backward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate" :limit (within-days 7)) 8 "outputs/rolling-backward-7days.csv"))
