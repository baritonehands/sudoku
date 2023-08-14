(ns sudoku.core
  (:require [clojure.set :as set])
  (:gen-class))

(defn row [[x y]]
  (for [dy (range 0 9) :when (not= y dy)]
    [x dy]))

(defn col [[x y]]
  (for [dx (range 0 9) :when (not= x dx)]
    [dx y]))

(def mods [[0 1 2] [-1 0 1] [-2 -1 0]])

(defn square [[x y]]
  (for [dx (mods (mod x 3))
        dy (mods (mod y 3))
        :when (or (not= 0 dx) (not= 0 dy))]
    [(+ dx x) (+ dy y)]))

(def legal (set (range 1 10)))

(defn available [board spots]
  (set/difference
    legal
    (set (map (comp :spot board) spots))))

(defn random-spots [board pos]
  (shuffle (set/intersection
             (available board (row pos))
             (available board (col pos))
             (available board (square pos)))))

(defn print-board [board]
  (println (apply str (repeat 37 "-")))
  (doseq [x (range 0 9)]
    (print "|")
    (doseq [y (range 0 9)]
      (print "" (or (:spot (board [x y])) ".") "|"))
    (print "\n")
    (println (apply str (repeat 37 "-")))))

(def spots (vec (for [y (range 0 9)
                      x (range 0 9)]
                  [x y])))

(defn build []
  (loop [board {}
         idx 0]
    (let [pos (spots idx)
          spot (or (board pos)
                   {:opts (random-spots board pos)})
          opts (:opts spot)]
      (cond
        (= idx 80) (assoc board pos (assoc spot :spot (first opts)))
        (seq opts) (recur (assoc board pos (assoc spot :spot (first opts)))
                          (inc idx))
        :else (recur (-> (dissoc board pos)
                         (update-in [(spots (dec idx)) :opts] pop))
                     (dec idx))))))

(defn -main
  "Generate and print a sudoku board."
  [& _]
  (print-board (time (build))))
