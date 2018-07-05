(ns cells.core
  (:require [cells.util :as u]
            [cells.cell :as c]
            [cells.io :as cio]
            [cells.stats :as s]
            [clojure.pprint])
  (:import [com.kimbsy.sim.BaseSim]
           [com.kimbsy.sim.BaseSimFrame]
           [com.kimbsy.sim.sprite.BaseSprite])
  (:gen-class))

(def state (atom {:cells (take 10 (repeatedly c/starting-cell))
                  :food {}
                  :running true
                  :stats false}))

(defn update-all [state]
  (if (:running state)
    (assoc state
           :cells (->> (:cells state)
                       (map c/update-cell)
                       (reduce c/breed [])
                       (filter c/alive?)))
    state))

(defn paint-all [g2d]
  (doseq [c (:cells @state)]
    (c/draw-cell g2d c))
  (if (:stats @state)
    (s/draw-stats g2d @state)))

(defn proxy-sim []
  (proxy [com.kimbsy.sim.BaseSim] []
    (update []
      (swap! state update-all))
    (onKeyTyped [e]
      (swap! state cio/handle-key-press e))))

(defn proxy-frame [sim]
  (proxy [com.kimbsy.sim.BaseSimFrame] [sim (.getTitle sim)]
    (paint [g]
      (.drawImage g (.-backBuffer this) 0 0 this)
      (let [g2d (.-g2d this)]
        (.setTransform g2d (.-identity this))
        (.setColor g2d java.awt.Color/black)
        (.fillRect g2d 0 0 u/width u/height)
        (paint-all g2d)))))

(defn run []
  (let [sim (proxy-sim)]
    (.setWindowSize sim u/width u/height)
    (.setTitle sim "cells")
    (.setFrameDelay sim 50)
    (.setSimFrame sim (proxy-frame sim))
    (.init sim)))

(defn -main
  [& args]
  (run))
