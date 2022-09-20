(ns clojask_examples.enhanced-reshape
  (:require [clojask.dataframe :as ck]
            [clojask.extensions.reshape :refer [melt dcast]]
            [clojask.extensions.bind :refer [rbind-csv cbind-csv]]))
;; Content Below
;; find more explicit explanation here
;; https://cran.r-project.org/web/packages/data.table/vignettes/datatable-reshape.html#enhanced-new-functionality
(defn -main
  []
  ;; enhanced melt
  (def x (ck/dataframe "resources/melt.csv"))
  ;; some operations to x
  (ck/rename-col x "dob_child1" "child1")
  (ck/rename-col x "dob_child2" "child2")
  (ck/rename-col x "dob_child3" "child3")
  (melt x "outputs/1.csv" ["family_id" "age_mother"] ["child1" "child2" "child3"] :measure-name "child" :value-name "dob")
  ;; x and y are from the same source
  (def y (ck/dataframe "resources/melt.csv"))
  (ck/rename-col x "gender_child1" "child1")
  (ck/rename-col x "gender_child2" "child2")
  (ck/rename-col x "gender_child3" "child3")
  (melt x "outputs/2.csv" ["family_id" "age_mother"] ["child1" "child2" "child3"] :measure-name "child" :value-name "gender")
  (def z (cbind-csv "outputs/1.csv" "outputs/2.csv"))
  ;; you can rename the column names of z here
  ;; skipped
  (ck/compute z 8 "outputs/melt_result.csv" :select ["family_id1" "age_mother1" "child1" "dob" "gender"])

  ;; enhanced dcast
  (def a (ck/dataframe "resources/dcast.csv"))
  (def dob (dcast a "outputs/1.csv" ["family_id" "age_mother"] "child" "dob" ["1" "2" "3"] :vals-name ["child1" "child2" "child3"]))
  (def b (ck/dataframe "resources/dcast.csv"))
  (def gender (dcast b "outputs/2.csv" ["family_id" "age_mother"] "child" "gender" ["1" "2" "3"] :vals-name ["child1" "child2" "child3"]))
  (def res (ck/inner-join dob gender ["family_id" "age_mother"] ["family_id" "age_mother"] :col-prefix ["dob" "gender"]))
  ;; you can rename the column names of res here
  ;; skipped
  (ck/compute res 8 "outputs/dcast_result.csv" :exclude ["dob_family_id" "dob_age_mother"]))