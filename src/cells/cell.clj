(ns cells.cell
  (:import [java.awt.Point])
  (:require [cells.util :as u]))

(defn gen-cell [] {:pos (u/rand-pos)
                   :size {:w 4
                          :h 4}})

(defn draw-cell [g2d cell]
  (.setColor g2d java.awt.Color/white)
  (.drawRect g2d
             (get-in cell [:pos :x])
             (get-in cell [:pos :y])
             (get-in cell [:size :w])
             (get-in cell [:size :h])))

(defn update-pos [pos]
  (assoc pos
         :x (+ (:x pos) (- (rand-int 3) 1))
         :y (+ (:y pos) (- (rand-int 3) 1))))

(defn update-cell [cell]
  (assoc cell :pos (u/bound (update-pos (:pos cell)))))
