(ns aoc-2021.day-2
(:require [aoc-2021.utils :refer [read-input read-test-input]]
          [clojure.string :as str]))

(def testinput
  (read-test-input "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2"))

(defn process-moves [moves acc]
  (let [move (str/split (first moves) #"\ ")
        dir (first move)
        len (read-string (second move))
        acc (case dir
              "forward" (update acc :x + len)
              "down" (update acc :y + len)
              "up" (update acc :y - len)
              acc)]
    (if (= 1 (count moves))
      (* (:x acc) (:y acc))
      (process-moves (rest moves) acc))))

(defn process-moves-with-aim [moves aim acc]
  (let [move (str/split (first moves) #"\ ")
        dir (first move)
        len (read-string (second move))
        aim (case dir
              "down" (+ aim len)
              "up" (- aim len)
              aim)  
        acc (case dir
              "forward" (-> acc
                            (update :x + len)
                            (update :y + (* len aim)))
              acc)]
    (if (= 1 (count moves))
      (* (:x acc) (:y acc))
      (process-moves-with-aim (rest moves) aim acc))))

(defn part-1 [input]
  (let [moves input]
    (process-moves moves {:x 0 :y 0})))

(defn part-2 [input]
  (process-moves-with-aim input 0 {:x 0 :y 0}))

(part-1 (read-input "resources/day-2-input.txt"))
(part-2 (read-input "resources/day-2-input.txt"))

