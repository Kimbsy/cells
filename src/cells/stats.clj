(ns cells.stats)

(defn display
  [g2d stats]
  (.setColor g2d (java.awt.Color. 0 0 0 150))
  (.fillRect g2d 10 10 200 (+ (* 15 (count stats)) 10))
  (.setColor g2d java.awt.Color/WHITE)
  (doseq [[i s] (map-indexed vector stats)]
    (.drawString g2d s 15 (+ (* i 15) 25))))

(defn draw-stats
  [g2d state]
  (let [cells (:cells state)
        size (count cells)]
    (if (> size 0)
      (let [max-age (:age (apply max-key :age cells))
            max-gen (:gen (apply max-key :gen cells))
            average-cell-energy (/ (reduce + (map :energy cells)) size)
            remaining-energy (reduce + (map #(reduce + %) (:food state)))]
        (display g2d [(str "Population size: " size)
                      (str "Max age: " max-age)
                      (str "Max generation: " max-gen)
                      (str "Average cell energy: " (int average-cell-energy))
                      (str "Remaining energy: " remaining-energy)])))))
