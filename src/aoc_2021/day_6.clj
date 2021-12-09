(ns aoc-2021.day-6
  (:require [aoc-2021.utils :refer [read-input read-test-input]]
            [clojure.string :as str]))

(def testinput ["3,4,3,1,2"])

(defn get-fishes [input]
  (map read-string (str/split (first input) #"\,")))

(defn part-1 [input]
  (let [fishes (map read-string (str/split (first input) #"\,"))]
    (count (loop [fishes fishes
           days 80]
      (if (= 0 days)
        fishes 
        (let [fishes (map (fn [fish] (if (= 0 fish)
                                 [6 8]
                                 [(dec fish)])) fishes)
              fishes (concat (mapv first fishes) (remove nil? (mapv second fishes)))]
        (recur fishes
               (dec days)))))))) 

(defn part-2 [input]
  (let [fishes (get-fishes input)
        days 80]
    (loop [days 256
           fishes (into {} (map (fn [[k v]] {k (count v)}) (group-by identity fishes)))]
      (if (= 0 days)
        (apply + (vals fishes))
        (recur (dec days)
               {0 (get fishes 1) 
                1 (get fishes 2)
                2 (get fishes 3)
                3 (get fishes 4)
                4 (get fishes 5)
                5 (get fishes 6)
                6 (+ (or (get fishes 7) 0) (or (get fishes 0) 0))
                7 (get fishes 8)
                8 (get fishes 0)})))))

(part-1 (read-input "resources/day-6-input.txt"))
(part-2 (read-input "resources/day-6-input.txt"))

