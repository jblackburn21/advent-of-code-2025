(ns y2025.d4
  (:require [clojure.test :as t :refer [deftest]])
  (:require [clojure.string :as str]))

;; PROBLEM LINK https://adventofcode.com/2025/day/4

;; Generator Logic

;; Solution Logic

;; Entry Points

(defn generator
  "The generator fn is used to parse your input into. The output of this fn will be passed into each of the solving fns"
  [input]
    (->> (str/split-lines input)
       (mapcat (fn [y-idx y-row]
                 (keep-indexed (fn [x-idx x-char]
                                 (when (= x-char "@")
                                   [x-idx y-idx]))
                               (str/split y-row #"")))
               (range))
       (set)))

(defn solve-part-1
  "The solution to part 1. Will be called with the result of the generator"
  [input]
  
;;   (doseq [coord input]
;;     (println "Coord: " coord))
  
    (reduce (fn [acc [x y]]
            (let [adjacent [[(dec x) y] [(inc x) y] 
                           [x (dec y)] [x (inc y)]
                           [(dec x) (dec y)] [(inc x) (dec y)]
                           [(dec x) (inc y)] [(inc x) (inc y)]]
                  adjacent-count (count (filter #(contains? input %) adjacent))]
              (if (< adjacent-count 4)
                (inc acc)
                acc)))
          0
          input)

  )

(defn solve-part-2
  "The solution to part 2. Will be called with the result of the generator"
  [input]
  
      (loop [remaining input
         total-removed 0]
    (let [to-remove (set (filter (fn [[x y]]
                                   (let [adjacent [[(dec x) y] [(inc x) y]
                                                  [x (dec y)] [x (inc y)]
                                                  [(dec x) (dec y)] [(inc x) (dec y)]
                                                  [(dec x) (inc y)] [(inc x) (inc y)]]
                                         adjacent-count (count (filter #(contains? remaining %) adjacent))]
                                     (< adjacent-count 4)))
                                 remaining))]
      (if (empty? to-remove)
        total-removed
        (recur (clojure.set/difference remaining to-remove)
               (+ total-removed (count to-remove))))))
  
  )

;; Tests
;; Use tests to verify your solution. Consider using the sample data provided in the question

(deftest pt1-test
  (def sample-input "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.")
  (t/is (= 13 (solve-part-1 (generator sample-input)))))

(deftest pt2-test
  (def sample-input "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.")
  (t/is (= 43 (solve-part-2 (generator sample-input)))))
