(ns cells.food
  (:require [cells.util :as u]))

(def max-starting 20)

(defn starting-food []
  (vec (take u/width (repeatedly
                      (fn []
                        (vec (take u/height (repeatedly
                                             (fn []
                                               (rand-int max-starting))))))))))

(defn absorb
  [output cell]
  (let [x (get-in cell [:pos :x])
        y (get-in cell [:pos :y])
        available (-> (:food output)
                      (nth x)
                      (nth y))]
    (assoc output
           :cells
           (conj (:cells output)
                 (assoc cell :energy (+ (:energy cell) available)))
           :food (assoc-in (:food output) [x y] 0))))

(defn feed
  [{:keys [cells food]}]
  (if (empty? cells)
    {:cells []
     :food food}
    (reduce (fn [output cell] (absorb output cell)) {:food food} cells)))

(defn draw-food
  [f i j g2d]
  (.setColor g2d (java.awt.Color. (int (* f (/ 100 (dec max-starting))))
                                  (int (* f (/ 100 (dec max-starting))))
                                  (int (* f (/ 255 (dec max-starting))))))
  (.drawLine g2d i j i j))

(defn draw-col
  [col i g2d]
  (doseq [[j f] (map-indexed vector col)]
    (draw-food f i j g2d)))

(defn draw
  [g2d state]
  (doseq [[i col] (map-indexed vector (:food state))]
    (draw-col col i g2d)))
