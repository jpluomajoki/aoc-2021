(ns aoc-2021.day-3
  (:require [aoc-2021.utils :refer [read-test-input read-input]]))

(def testinput (read-test-input "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010\n"))

(defn get-count-of-bits [reports]
  (map frequencies (mapv (fn [idx] (map (fn [report] (nth report idx)) reports)) (range (count (first reports))))))

(defn get-most-common-bits [counts]
  (apply str (map (fn [count]
        (key (apply max-key val count))) counts)))

(defn get-least-common-bits [counts]
  (apply str (map (fn [count]
        (key (apply min-key val count))) counts)))

(defn bits-to-int [bits]
  (read-string (str "2r" bits)))

(defn part-1 [input]
  (let [counts (get-count-of-bits input)
        mcbs (get-most-common-bits counts)
        lcbs (get-least-common-bits counts)
        mcb-int (bits-to-int mcbs)
        lcb-int (bits-to-int lcbs)
        ]
    (* mcb-int lcb-int) 
  ))

(part-1 testinput)
(part-1 (read-input "resources/day-3-input.txt"))
