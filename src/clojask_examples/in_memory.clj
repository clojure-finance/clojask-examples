(ns clojask-examples.in-memory
  (:require [clojask.dataframe :as ck]
            [clojask-io.input :as input]
            [clojask-io.output :as output]
            [clojask.api.aggregate :as agg]
            [clojask.api.gb-aggregate :as gb-agg])
  (:import (java.util Date)))

(defn time-diff
  [start end]
  (/ (- (.getTime end) (.getTime start)) 60000))

;; content below
(defn -main
  []
  (def df (ck/dataframe (fn [] (input/read-excel "resources/course timetable.xlsx" "Sheet1" :stat true))))
;;   (ck/filter df "ACAD_CAREER" #(= % "UGME"))
  (ck/filter df "THU" #(not= % nil))
  (ck/set-type df "START TIME" "datetime:HH:mm")
  (ck/set-type df "END TIME" "datetime:HH:mm")
  (ck/operate df time-diff ["START TIME" "END TIME"] "diff")
  (let [groupby-key ["COURSE CODE" "CLASS SECTION" "START DATE"]]
    (ck/group-by df groupby-key)
    (ck/aggregate df #(reduce + %) "diff" "total_mins")
    (def res (ck/compute df 8 nil :order true))
    (def res-df (ck/dataframe (fn [] res)))
    (ck/set-type df "START TIME" "string")
    (ck/set-type df "END TIME" "string")
    (def join-df (ck/left-join df res-df groupby-key groupby-key))
    (ck/compute join-df 8 "outputs/in_mem.csv" :select [])
    ;; (ck/)
    )
  )

;; todo
;; output function automatic
;; format option for in memory