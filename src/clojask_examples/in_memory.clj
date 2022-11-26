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


;; todo
;; output function automatic
;; format option for in memory

;; Content Below
(defn -main
  []
  (def df (ck/dataframe (fn [] (input/read-excel "resources/course timetable.xlsx" "Sheet1" :stat true))))
  (ck/filter df "THU" #(not= % nil))
  (ck/set-type df "START TIME" "datetime:HH:mm")
  (ck/set-type df "END TIME" "datetime:HH:mm")
  (ck/operate df time-diff ["START TIME" "END TIME"] "diff")
  (let [groupby-key ["COURSE CODE" "CLASS SECTION" "START DATE"]]
    (ck/group-by df groupby-key)
    (ck/aggregate df #(reduce + %) "diff" "MINS/WEEK")
    (def res (ck/compute df 8 nil :order true))
    (def res-df (ck/dataframe (fn [] res)))
    ;; reinitialize df
    (def df (ck/dataframe (fn [] (input/read-excel "resources/course timetable.xlsx" "Sheet1" :stat true))))
    (def join-df (ck/right-join df res-df groupby-key groupby-key))
    (def res2 (ck/compute join-df 8 nil :select ["1_COURSE CODE" "1_TERM" "1_START DATE" "1_END DATE" "1_START TIME" "1_END TIME" "2_MINS/WEEK"]))
    (output/write-excel "outputs/in-mem.xls" "Sheet1" res2)
    )
  )