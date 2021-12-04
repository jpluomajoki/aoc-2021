(ns aoc-2021.day-3
  (:require [aoc-2021.utils :refer [read-test-input read-input]]))

(def testinput (read-test-input "00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010\n"))

(defn get-count-of-bits [reports]
  (map #(into (sorted-map) %) (map frequencies (mapv (fn [idx] (map (fn [report] (nth report idx)) reports)) (range (count (first reports)))))))

(defn get-most-common-bits [counts]
  (apply str (map (fn [count]
        (key (apply max-key val count))) counts)))

(defn get-least-common-bits [counts]
  (apply str (map (fn [count]
        (key (apply min-key val (into {} (reverse count))))) counts)))

(defn bits-to-int [bits]
  (read-string (str "2r" bits)))

(defn filter-by-bit-in-pos [reports pos bit]
  (filter (fn [report]
           (= (nth report pos) bit)) reports))

(defn part-1 [input]
  (let [counts (get-count-of-bits input)
        mcbs (get-most-common-bits counts)
        lcbs (get-least-common-bits counts)
        mcb-int (bits-to-int mcbs)
        lcb-int (bits-to-int lcbs)]
    (* mcb-int lcb-int)))

(defn part-2 [input]
  (loop [oxygen input
         co2 input
         idx 0]
    (let [o-counts (get-count-of-bits oxygen)
          o-mcbs (into [] (get-most-common-bits o-counts)) 
          c-counts (get-count-of-bits co2)
          c-lcbs (into [] (get-least-common-bits c-counts))]
      (if (and (= 1 (count oxygen)) (= 1 (count co2)))
        (* (bits-to-int (first oxygen)) (bits-to-int (first co2)))
        (recur
            (filter-by-bit-in-pos oxygen idx (nth o-mcbs idx)) 
            (filter-by-bit-in-pos co2 idx (nth c-lcbs idx)) 
            (inc idx))))))

;; Part 1
(part-1 testinput)
(part-1 (read-input "resources/day-3-input.txt"))

;; Part 2
(part-2 testinput)
(part-2 (read-input "resources/day-3-input.txt"))
