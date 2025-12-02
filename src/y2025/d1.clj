(ns y2025.d1
  (:require [clojure.test :as t :refer [deftest]])
  (:require [clojure.string :as str]))

;; PROBLEM LINK https://adventofcode.com/2025/day/1

;; Generator Logic

;; Solution Logic

;; Entry Points

(defn generator
  "The generator fn is used to parse your input into. The output of this fn will be passed into each of the solving fns"
  [input]
  (->> (str/split-lines input)
         (map (fn [line]
                (let [dir (subs line 0 1)
                      num (parse-long (subs line 1))]
                  (if (= dir "L") (- num) num)))))
  )

(defn solve-part-1
  "The solution to part 1. Will be called with the result of the generator"
  [input]
  (def dial-num 50)

  (reduce (fn [acc rotation]
            (let [curr-num (mod (+ (:curr-num acc) rotation) 100)]

            ;; (println (str "Curr num: " curr-num))

            (if (= curr-num 0)
              {:curr-num curr-num :count (+ (:count acc) 1)}
              {:curr-num curr-num :count (:count acc)}
              )))


          {:curr-num dial-num :count 0}
          input)
  )

(defn solve-part-2
  "The solution to part 2. Will be called with the result of the generator"
  [input]
  (def dial-num 50)


  (reduce (fn [acc rotation]
            (let [prev-num (:curr-num acc)
                  rotated-num (+ prev-num rotation)
                  next-num (mod rotated-num 100)
                  zero-passes (quot (abs rotation) 100)
                  landed-on-zero (if (= next-num 0) 1 0)

                  crossed-zero (if (and (not= prev-num 0) (not= next-num 0))
                                   (if (or (and (< rotation 0) (> next-num prev-num))
                                           (and (> rotation 0) (< next-num prev-num)))
                                     1
                                     0)
                                   0)
                  ]

            ;; (println (str "Previous num: " prev-num " Rotation: " rotation " Next num: " next-num " Zero passes: " zero-passes " Crossed zero: " crossed-zero " Landed on zero: " landed-on-zero))

            {:curr-num next-num :count (+ (acc :count) crossed-zero landed-on-zero zero-passes)}
    ))

          {:curr-num dial-num :count 0}
          input)
  
  )

;; Tests
;; Use tests to verify your solution. Consider using the sample data provided in the question

(deftest pt1-test
  (def sample-input "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")
  (t/is (= 3 (:count (solve-part-1 (generator sample-input))))))

(deftest pt2-test
  (def sample-input "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")
  (t/is (= 6 (:count (solve-part-2 (generator sample-input))))))
