(ns cells.io)

(defn handle-key-press
  [state e]
  (let [c (.getKeyChar e)]
    (cond (= c \ )
          (do (println "toggling pause")
              (assoc state :running (not (:running state))))
          (= c \s)
          (do (println "toggling stats")
              (assoc state :stats (not (:stats state))))
          :else
          (do (println "unused key")
              state))))
