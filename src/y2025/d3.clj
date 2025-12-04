(ns y2025.d3
  (:require [clojure.test :as t :refer [deftest]])
  (:require [clojure.string :as str]))

;; PROBLEM LINK https://adventofcode.com/2025/day/3

;; Generator Logic

;; Solution Logic

;; Entry Points

(defn generator
  "The generator fn is used to parse your input into. The output of this fn will be passed into each of the solving fns"
  [input]
  (->> (str/split-lines input)
         (map (fn [bank]
              (->> bank
                   (map-indexed (fn [idx ch]
                                  {:idx idx :val (parse-long (str ch))}))
                   (into []))))))


(defn find-top-n-digits
  "Find the top n digits. The first digit is picked such that there are at least n-1 items following it.
   Subsequent digits are picked from the elements following the previous selection, maintaining the constraint."
  [bank n]
  (let [bank-size (count bank)
        result (reduce (fn [{:keys [candidates acc]} _]
                         (let [needed-after (- n (count acc) 1)
                               valid-candidates (filter (fn [{:keys [idx]}]
                                                          (>= (- bank-size 1 idx) needed-after))
                                                        candidates)]
                           (if (empty? valid-candidates)
                             (reduced {:candidates [] :acc acc})  ; short-circuit
                             (let [max-val (apply max (map :val valid-candidates))
                                   pick (->> valid-candidates
                                             (filter #(= (:val %) max-val))
                                             (sort-by :idx)
                                             first)
                                   next-candidates (drop-while #(<= (:idx %) (:idx pick)) candidates)]
                               {:candidates next-candidates :acc (conj acc pick)}))))
                       {:candidates bank :acc []}
                       (range n))]
    (when (= (count (:acc result)) n)
      (parse-long (apply str (map :val (:acc result)))))))

(defn solve-part-1
  "The solution to part 1. Will be called with the result of the generator"
  [input]

    ;; (let [max-joltage-per-bank (map (fn [bank]
    ;;                                 (let [first-max (apply max-key :val (reverse (butlast bank))) ;; max-key is returning the last max, so reverse to get first
    ;;                                         after (drop (inc (:idx first-max)) bank)
    ;;                                         second-max (apply max-key :val after)]
    ;;                                 (->> [first-max second-max] 
    ;;                                     (map :val)
    ;;                                     (apply str)
    ;;                                     (parse-long)
    ;;                                 )))
    ;;                                 ;; [first-max second-max] ))
    ;;                                 input)]

    (let [max-joltage-per-bank (map #(find-top-n-digits % 2) input)]

        ;; (doseq [max-joltage max-joltage-per-bank]
        ;;     (println "Max joltage: " max-joltage))

        (reduce + max-joltage-per-bank)
  )
)

(defn solve-part-2
  "The solution to part 2. Will be called with the result of the generator"
  [input]
  
  (let [max-joltage-per-bank (map #(find-top-n-digits % 12) input)]

        ;; (doseq [max-joltage max-joltage-per-bank]
        ;;     (println "Max joltage: " max-joltage))

        (reduce + max-joltage-per-bank)
  )
)

;; Tests
;; Use tests to verify your solution. Consider using the sample data provided in the question

;; Max joltage:  [{:idx 0, :val 9} {:idx 1, :val 8}]
;; Max joltage:  [{:idx 0, :val 8} {:idx 14, :val 9}]
;; Max joltage:  [{:idx 13, :val 7} {:idx 14, :val 8}]
;; Max joltage:  [{:idx 6, :val 9} {:idx 11, :val 2}]

;; 98 + 89 + 78 + 92 = 357

(deftest pt1-test
  (def sample-input "987654321111111
811111111111119
234234234234278
818181911112111")
  (t/is (= 357 (solve-part-1 (generator sample-input)))))

(deftest pt2-test
  (def sample-input "987654321111111
811111111111119
234234234234278
818181911112111")
  (t/is (= 3121910778619 (solve-part-2 (generator sample-input)))))