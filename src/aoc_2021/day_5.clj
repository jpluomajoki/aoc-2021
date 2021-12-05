(ns aoc-2021.day-5
  (:require [aoc-2021.utils :refer [read-test-input read-input]]
            [clojure.string :as str]))

(def testinput (read-test-input "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2\n"))

; Points are a vec where the first element is the starting point
; And the second element is the end.
(defn get-points [line]
  (str/split line #"\ -> "))

(defn get-coords [point]
  [(mapv read-string (str/split (first point) #","))
  (mapv read-string (str/split (second point) #","))])

(defn remove-diagonal-lines [lines]
  (filter (fn [[[from-x from-y] [to-x to-y]]]
            (or (= from-x to-x) (= from-y to-y)))
          lines))

(defn range-or-repeat-inclusive [v1 v2]
  (if (= v1 v2)
    (repeat v1)
    (if (< v1 v2)
      (range v1 (inc v2))
      (reverse (range v2 (inc v1))))))

(defn make-line [[[from-x from-y] [to-x to-y]]]
  (partition 2 (interleave (range-or-repeat-inclusive from-x to-x)
                           (range-or-repeat-inclusive from-y to-y))))

(defn part-1 [input]
  (let [points (->> input
                   (map get-points)
                   (map get-coords)
                   (remove-diagonal-lines)
                   (map make-line)
                   (flatten)
                   (partition 2))]
  (count (filter (fn [[k v]] (> v 1)) (frequencies points)))))

(defn part-2 [input]
  (let [points (->> input
                   (map get-points)
                   (map get-coords)
                   (map make-line)
                   (flatten)
                   (partition 2))]
(count (filter (fn [[k v]] (> v 1)) (frequencies points)))))

(part-1 testinput)
(part-1 (read-input "resources/day-5-input.txt"))

(part-2 testinput)
(part-2 (read-input "resources/day-5-input.txt"))
