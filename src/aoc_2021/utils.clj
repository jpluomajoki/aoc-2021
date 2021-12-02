(ns aoc-2021.utils
  (:require [clojure.string :as str]))

(defn read-input [file]
  (str/split (slurp file) #"\n"))

(defn read-test-input [input]
  (str/split input #"\n"))
