(ns cells.core
  (:import [com.kimbsy.sim.BaseSim]
           [com.kimbsy.sim.sprite.BaseSprite]
           [java.awt.Point])
  (:gen-class))

(defn red-square []
  (proxy [com.kimbsy.sim.sprite.BaseSprite]
      [(java.awt.Point. 200 200)]
    (draw [g2d]
      (.setColor g2d java.awt.Color/white)
      (.fillRect g2d
                 (.-x (.getPos this))
                 (.-y (.getPos this))
                 10
                 10))))

(defn get-sprites []
  #{(red-square)})

(defn run []
  (let [sim (proxy [com.kimbsy.sim.BaseSim] []
              (initSprites []
                (.setSprites this (get-sprites)))
              (update []
                (println "updating")))]
    (.setWindowSize sim 840 840)
    (.setTitle sim "cells")
    (.setFrameDelay sim 500)
    (.init sim)))

(defn -main
  [& args]
  (run))
