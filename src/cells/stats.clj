(ns cells.stats)

(defn draw-stats
  [g2d state]
  (.setColor g2d java.awt.Color/LIGHT_GRAY)
  (let [cells (:cells state)
        size (count cells)]
    (.drawString g2d (str "Population size: " size) 10 30)
    (if (> size 0)
      (let [max-age (:age (apply max-key :age cells))
            max-gen (:gen (apply max-key :gen cells))
            average-energy (/ (reduce + (map :energy cells)) size)]
        (.drawString g2d (str "Max age: " max-age) 10 45)
        (.drawString g2d (str "Max generation: " max-gen) 10 60)
        (.drawString g2d (str "Average energy: " (int average-energy)) 10 75)))))
