(ns clojask-examples.examples-test
  "Runs every example and checks the files it writes to outputs/."
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojask-examples.basic-tutorial :as basic]
            [clojask-examples.groupby-aggregate :as gb]
            [clojask-examples.ordinary-join :as ordinary-join]
            [clojask-examples.outer-join :as outer-join]
            [clojask-examples.rolling-join :as rolling-join]
            [clojask-examples.enhanced-reshape :as reshape]
            [clojask-examples.timezone :as timezone]
            [clojask-examples.in-memory :as in-memory]
            [clojask-examples.techmldataset :as tmd]
            [clojask-io.input :as input]
            [tech.v3.dataset :as ds]))

(defn- read-rows
  "Rows of a CSV output (no quoted fields in these examples), header first."
  [path]
  (mapv #(str/split % #"," -1) (str/split-lines (slurp path))))

(defn- as-maps
  [path]
  (let [[header & rows] (read-rows path)]
    (mapv #(zipmap header %) rows)))

(defn- run-example
  [main]
  (io/make-parents "outputs/x")
  (main))

(deftest basic-tutorial
  (run-example basic/-main)
  (testing "salaries <= 800, raised by 20%"
    (is (= [["1" "360.0"] ["2" "720.0"] ["5" "960.0"] ["6" "840.0"]]
           (mapv (juxt #(get % "Employee") #(get % "Salary")) (as-maps "outputs/basic.csv")))))
  (testing "cats of at most 5 kg"
    (is (= [["Cat's name" "Weight(kg)"] ["Lily" "2.3"] ["Meow" "3.2"]]
           (read-rows "outputs/basic3.csv")))))

(deftest groupby-aggregate
  (run-example gb/-main)
  (is (= #{{"Department" "11" "max(UpdateDate)" "2020/12/12" "sum(Salary)" "50900"}
           {"Department" "12" "max(UpdateDate)" "2020/12/05" "sum(Salary)" "1900"}
           {"Department" "13" "max(UpdateDate)" "2020/12/03" "sum(Salary)" "800"}
           {"Department" "21" "max(UpdateDate)" "2020/12/05" "sum(Salary)" "700"}}
         (set (as-maps "outputs/gb-agg.csv")))))

(deftest ordinary-join
  (run-example ordinary-join/-main)
  (is (= [["1" "300" "20"] ["2" "600" "15"] ["3" "900" "5"] ["7" "50000" "30"]]
         (rest (read-rows "outputs/inner-join.csv"))))
  (is (= 7 (count (as-maps "outputs/left-join.csv"))))
  (is (= ["1" "2" "3" "7" "8"]
         (mapv #(get % "2_Employee") (as-maps "outputs/right-join.csv")))))

(deftest outer-join
  (run-example outer-join/-main)
  (let [rows (as-maps "outputs/outer-join.csv")]
    (is (= 8 (count rows)))
    (testing "the row only in y keeps its Employee"
      (is (= [{"2_Employee" "8" "2_EmployeeName" "Leo"}]
             (->> rows
                  (filter #(= "" (get % "1_Employee")))
                  (mapv #(select-keys % ["2_Employee" "2_EmployeeName"]))))))))

(deftest rolling-join
  (run-example rolling-join/-main)
  (let [matched (fn [path]
                  (->> (as-maps path)
                       (keep #(when (not= "" (get % "2_Employee"))
                                [(get % "1_Employee") (get % "2_UpdateDate")]))
                       vec))]
    (is (= [["1" "2020/12/10"] ["3" "2020/12/03"]]
           (matched "outputs/rolling-forward.csv")))
    (is (= [["2" "2020/12/05"] ["3" "2020/12/03"] ["7" "2020/12/11"]]
           (matched "outputs/rolling-backward.csv")))
    (testing "Amy's 15-day gap is over the 7-day limit"
      (is (= [["2" "2020/12/05"] ["3" "2020/12/03"]]
             (matched "outputs/rolling-backward-7days.csv"))))))

(deftest enhanced-reshape
  (run-example reshape/-main)
  (testing "melt matches the long form in resources/dcast.csv"
    (let [long-form (set (map #(vector (get % "family_id") (str "child" (get % "child"))
                                       (get % "dob") (get % "gender"))
                              (as-maps "resources/dcast.csv")))]
      (is (= long-form
             (set (map #(vector (get % "family_id1") (get % "child1") (get % "dob") (get % "gender"))
                       (as-maps "outputs/melt_result.csv")))))))
  (testing "rbind stacks the two halves back into resources/dcast.csv"
    (let [[header & rows] (read-rows "resources/dcast.csv")
          [rb-header & rb-rows] (read-rows "outputs/rbind_result.csv")]
      (is (= header rb-header))
      (is (= (concat (filter #(= "1" (nth % 2)) rows) (remove #(= "1" (nth % 2)) rows))
             rb-rows))))
  (testing "dcast matches the wide form in resources/melt.csv"
    (let [wide (set (map #(mapv % ["family_id" "dob_child1" "dob_child2" "dob_child3"
                                   "gender_child1" "gender_child2" "gender_child3"])
                         (as-maps "resources/melt.csv")))]
      (is (= wide
             (set (map #(mapv % ["gender_family_id" "dob_child1" "dob_child2" "dob_child3"
                                 "gender_child1" "gender_child2" "gender_child3"])
                       (as-maps "outputs/dcast_result.csv"))))))))

(deftest timezone
  (run-example timezone/-main)
  (is (= #{"2010-01-19 12:12:12" "2010-01-22 15:34:23" "2010-01-25 19:34:23" "2010-01-27 07:34:23"}
         (set (map first (rest (read-rows "outputs/timezone1.csv"))))))
  (is (= #{["2010-01-19 12:12:12" "11"] ["2010-01-22 15:34:23" "14"]
           ["2010-01-25 19:34:23" "9"] ["2010-01-27 07:34:23" "10"]}
         (set (rest (read-rows "outputs/timezone2.csv"))))))

(deftest in-memory
  (run-example in-memory/-main)
  (let [[header & rows] (:data (input/read-excel "outputs/in-mem.xlsx" "Sheet1"))]
    (is (= ["1_COURSE CODE" "1_TERM" "1_START DATE" "1_END DATE" "1_START TIME" "1_END TIME" "2_MINS/WEEK"]
           (vec header)))
    (is (= 98 (count rows)))
    (testing "MINS/WEEK sums the Thursday class minutes"
      (is (= ["DENT2005FY" "09:00" "17:20" "500"]
             (mapv (vec (first rows)) [0 4 5 6]))))))

(deftest techmldataset
  (run-example tmd/-main)
  (is (= 7 (ds/row-count tmd/df2))))
