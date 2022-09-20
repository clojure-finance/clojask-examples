(ns clojask-examples.timezone
  (:require [clojask.dataframe :as ck]
            [clojure.string :as str])
  (:import [java.util TimeZone]
           [java.text SimpleDateFormat]))

(def string-zone
  {"UTC" "UTC"
   "HKG" "Asia/Hong_Kong"
   "UK" "Europe/London"
   "DUB" "Asia/Dubai"
   "LA" "America/Los_Angeles"})

(defn get-utc-time
  "the input is a datetime string with timezone identifier as suffix"
  ;; suppose the input format is like this 2010-01-19 23:34:23 UTC
  [time-string]
  (let [time (subs time-string 0 (str/last-index-of time-string " "))
        timezone (subs time-string (inc (str/last-index-of time-string " ")))
        timezone (get string-zone timezone)
        iso-format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        tmp (.setTimeZone iso-format (TimeZone/getTimeZone timezone))
        time (.parse iso-format time)]
    time))

(defn get-timezone
  [time-string]
  (let [timezone (subs time-string (inc (str/last-index-of time-string " ")))
        ]
    timezone)
  )
(def utc-format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss"))
(.setTimeZone utc-format (TimeZone/getTimeZone "UTC"))
(defn utctime-formatter
  "the input is a vector, return is a format string"
  [utc-time]
  (.format utc-format utc-time)
  )

;; Content Below
(defn -main
  []
  (def df (ck/dataframe "resources/sales.csv"))
  (ck/operate df get-utc-time "datetime" "utc-time")
  (ck/operate df get-timezone "datetime" "timezone")
  (ck/set-formatter df "utc-time" utctime-formatter)
  (ck/group-by df "utc-time")
  (ck/compute df 8 "outputs/timezone1.csv")
  (ck/set-type df "sold" "int")
  (ck/aggregate df #(reduce + %) "sold")
  (ck/compute df 8 "outputs/timezone2.csv")
  )