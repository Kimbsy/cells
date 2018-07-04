(ns cells.util)

(def width 840)
(def height 840)

(defn rand-pos []
  {:x (rand-int width)
   :y (rand-int height)})

(defn bound [pos]
  (assoc pos
         :x (mod (:x pos) width)
         :y (mod (:y pos) height)))
