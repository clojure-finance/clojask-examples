(ns clojask-examples.timezone
  (:require [clojask.dataframe :as ck])
  (:import (java.time ZoneId)
           (java.time LocalDateTime)
           (java.time.format DateTimeFormatter)))

(def string-zone
  {"UTC" "UTC"
   "HKG" "ASIA/HONG_KONG"
   "UK" "Europe/London"
   "DUB" "UAE/Dubai"
   "LA" "America/Los_Angeles"})

(defn timezone-parser
  "the input is a datetime string with timezone identifier as suffix"
  ;; suppose the input format is like this 2010-01-19 23:34:23 UTC
  [time-string]
  (let [datetime (subs time-string 0 19)
        zone (subs time-string 19)
        localtime (LocalDateTime/parse datetime (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss"))]
    (.atZone localtime (ZoneId/of (get string-zone zone))))
  )

(defn timezone-formatter
  "the input is a zoneddatatime, return is a format string"
  [time-vec]
  )

(def main
  []
  (def df (ck/dataframe "resources/sales.csv"))
  )