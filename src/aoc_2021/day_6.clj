(ns aoc-2021.day-6
  (:require [aoc-2021.utils :refer [read-input read-test-input]]
            [clojure.string :as str]))

(def testinput ["3,4,3,1,2"])

(defn get-fishes [input]
  (str/split (first input) #"\,"))

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

(part-1 testinput)
(part-1 (read-input "resources/day-6-input.txt"))

