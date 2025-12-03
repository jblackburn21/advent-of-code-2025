(ns y2025.d2
  (:require [clojure.test :as t :refer [deftest]])
  (:require [clojure.string :as str]))

;; PROBLEM LINK https://adventofcode.com/2025/day/2

;; Generator Logic

;; Solution Logic

;; Entry Points

(defn generator
  "The generator fn is used to parse your input into. The output of this fn will be passed into each of the solving fns"
  [input]
  (->> (str/split input #",")
         (map (fn [range]
            (let [[first last] (str/split range #"-")]
                {:first (parse-long first) :last (parse-long last)})))))


(defn has-repeated-pattern? [id]
  (let [s (str id)
        len (count s)]
    (and (even? len)
         (= (subs s 0 (/ len 2))
            (subs s (/ len 2))))))

(defn has-repeating-pattern? [id]
  (let [s (str id)
        doubled (str s s)]
    (not= -1 (.indexOf doubled s 1 (- (count doubled) 1)))))

(defn solve-part-1
  "The solution to part 1. Will be called with the result of the generator"
  [input]
;;   (doseq [id-range input]
;;     (println "Range: " id-range)
;;   ))
  (reduce (fn [acc id-range]
            (->> (range (:first id-range) (inc (:last id-range)))
                 (filter has-repeated-pattern?)
                 (reduce + acc)))
          0
          input)
  )

(defn solve-part-2
  "The solution to part 2. Will be called with the result of the generator"
  [input]
  
    (reduce (fn [acc id-range]
            (->> (range (:first id-range) (inc (:last id-range)))
                 (filter has-repeating-pattern?)
                 (reduce + acc)))
          0
          input)
  )

;; Tests
;; Use tests to verify your solution. Consider using the sample data provided in the question

(deftest pt1-test
  (def sample-input "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")
  (t/is (= 1227775554 (solve-part-1 (generator sample-input)))))

(deftest pt2-test
  (def sample-input "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")
  (t/is (= 4174379265 (solve-part-2 (generator sample-input)))))

;; 1188511885

;; 1 188511885118851188 5