(ns cells.cell
  (:require [cells.util :as u]
            [cells.food :as f]))

(defn starting-cell [] {:pos (u/rand-pos)
                   :size {:w 4
                          :h 4}
                   :energy 6400
                   :gen 0
                   :age 0})

(defn alive? [cell]
  (>= (:energy cell) 0))

(defn draw [g2d cell]
  (.setColor g2d java.awt.Color/white)
  (.drawRect g2d
             (get-in cell [:pos :x])
             (get-in cell [:pos :y])
             (get-in cell [:size :w])
             (get-in cell [:size :h])))

(defn move [cell]
  (let [pos (:pos cell)]
    (assoc cell :pos (u/bound {:x (+ (:x pos) (dec (rand-int 3)))
                               :y (+ (:y pos) (dec (rand-int 3)))}))))

(defn age [cell]
  (assoc cell
         :energy (- (:energy cell) (rand-int 2))
         :age (inc (:age cell))))

(defn split-cell [cell]
  [(assoc cell :energy (/ (:energy cell) 2))
   (assoc cell
          :energy (/ (:energy cell) 2)
          :gen (inc (:gen cell))
          :age 0)])

(defn breed [cells cell]
  (if (> (:energy cell) 100)
    (apply conj cells (split-cell cell))
    (conj cells cell)))
