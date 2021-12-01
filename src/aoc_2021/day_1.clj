(ns aoc-2021.day-1
  (:require [clojure.string :as str]))

(def input (map read-string (str/split (slurp "resources/day-1-input.txt")
                      #"\n")))

(defn times-increased 
  ([measurements]
   (times-increased measurements 0 nil))
  ([measurements current-count previous]
  (if (= 0 (count measurements))
    current-count 
    (if (and (some? previous) (< previous (first measurements)))
      (times-increased (rest measurements) (inc current-count) (first measurements))
      (times-increased (rest measurements) current-count (first measurements))))))

(defn times-increased-window-3
  ([m]
   (times-increased-window-3 m 0 nil))
  ([measurements current-count previous]
   (if (> 3 (count measurements))
     current-count
     (if (and (some? previous) (< previous (+ (nth measurements 0)
                                              (nth measurements 1)
                                              (nth measurements 2))))
       (times-increased-window-3 (rest measurements) (inc current-count) (+ (nth measurements 0)
                                                                            (nth measurements 1)
                                                                            (nth measurements 2)))
       (times-increased-window-3 (rest measurements) current-count (+ (nth measurements 0)
                                                                      (nth measurements 1)
                                                                      (nth measurements 2)))))))

     (times-increased-window-3 [199 200 208 210 200 207 240 269 260 263])
(times-increased-window-3 input)

