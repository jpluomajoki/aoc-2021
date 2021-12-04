(ns aoc-2021.day-4
  (:require [aoc-2021.utils :refer [read-input]]
            [clojure.string :as str]
            [clojure.data :as data]
            [clojure.set :as set]))

(def testinput (read-input "resources/day-4-test-input.txt"))

(defn get-numbers [input]
  (str/split (first input) #"\,"))

(defn get-bingo-boards [input]
  (map (fn [board]
         (map (fn [line]
                (filter not-empty (str/split line #"\ ")))
              board)) 
       (partition 5 (filter not-empty (rest (rest input))))))

(defn get-rows [board]
  board)

(defn get-columns [board]
  (map
    (fn [idx]
      (map (fn [row]
             (nth row idx))
           board)) 
    (range 5)))

(defn matches? [rows columns numbers]
  (and (some? rows) (some? columns)
       (or (some true? (map #(set/subset? % (set numbers)) rows))
           (some true? (map #(set/subset? % (set numbers)) columns)))))

(defn calculate-score [board drawn-numbers]
  (* (read-string (last drawn-numbers))
     ;; I'm sure there is an easier way to do this.
     (apply + (map read-string (first (data/diff (set (flatten board)) (set drawn-numbers)))))))

(defn part-1 [input]
  (let [numbers (get-numbers input)
        boards (get-bingo-boards input)]
    (loop [drawn-numbers (take 5 numbers)]
      (if (= (count drawn-numbers) (count numbers))
        "No winner?" 
        (let [winner (loop [unchecked-boards boards]
                       (let [board (first unchecked-boards)
                             rows (get-rows board)
                             columns (get-columns board)
                             matches? (matches? rows columns drawn-numbers)]
                            (cond 
                              matches? board
                              (= 1 (count unchecked-boards)) nil
                              :default (recur (rest unchecked-boards)))))]
          (if winner
            (calculate-score winner drawn-numbers)
            (recur (take (inc (count drawn-numbers)) numbers))))))))

;; This 
(defn part-2 [input]
  (let [numbers (get-numbers input)
        boards (get-bingo-boards input)]
    (loop [drawn-numbers (take 5 numbers)
           winners {}
           unsolved-boards boards]
      (let [{solved-boards :solved-boards
             last-board :last-board
             last-draw :last-draw
             unsolved-boards :unsolved-boards}
            (loop [unchecked-boards unsolved-boards
                   solved-boards []
                   unsolved-boards unsolved-boards]
              (let [board (first unchecked-boards)
                    rows (get-rows board)
                    columns (get-columns board)
                    matches? (matches? rows columns drawn-numbers) ]
                (if (empty? unchecked-boards)
                  {:solved-boards solved-boards
                   :last-board (when (= 0 (count unsolved-boards)) (last solved-boards))
                   :last-draw (when (= 0 (count unsolved-boards)) drawn-numbers)
                   :unsolved-boards unsolved-boards}
                  (recur
                    (rest unchecked-boards)
                    (if matches?
                      (conj solved-boards board)
                      solved-boards)
                    (remove #(and matches? (= % board)) unsolved-boards)))))]
        (if (or (empty? unsolved-boards)
                (= (count drawn-numbers) (count numbers)))
          (calculate-score last-board last-draw) 
          (recur (take (inc (count drawn-numbers)) numbers)
                 (concat winners solved-boards)
                 unsolved-boards))))))

(part-1 testinput)
;; Takes 323ms on my laptop, horrible. But at least I won the qiant squid?
(part-1 (read-input "resources/day-4-input.txt"))
(part-2 testinput) 
;; And this one takes 1854ms. Oh god.
(part-2 (read-input "resources/day-4-input.txt"))
