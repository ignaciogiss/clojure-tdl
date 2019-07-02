(ns ^:figwheel-hooks life.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [cljs.core.async :refer [timeout <!]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  )
(defonce state (reagent/atom #{[-1 0] [-1 1] [0 -1] [0 0] [1 0]}))

;; Dado un par [x y], devuelve la collecion de vecinos en cada direccion
(defn neighbors
  [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1] 
        :when (not= [dx dy] [0 0])]
    [(+ x dx) (+ y dy)]))

;; Dada una poblacion, devulve la siguiente generacion 
;; siguiendo las reglas de Conway
(defn next-pop
  [pop]
  (let [all-neighbors (mapcat neighbors pop)
        neigh-count (frequencies all-neighbors)]
    (set (for [[cell count] neigh-count
               :when (or (= count 3)
                         (and (= count 2)
                              (pop cell)))]
           cell))))
;; Comienza el proceso de evolucion. Cambia de estado cada 10ms
(defn evolve
     []
  (go-loop []
    (<! (timeout 10))
    (swap! state next-pop)
    (recur)))

;; Define la grilla donde vive la poblacion
(defn grid []
  [:svg {
         :style {:background "black"}
          :width 750
         :height 750
         :viewBox "-375 -375 750 750"}
   (for [[x y] @state]
     [:rect {:fill "white"
             :width 20
             :height 20
             :x (* x 20)
             :y (* y 20)}])]
  )

;; renderiza la grid
(reagent/render [grid] (.-body js/document))

;; corre la funcion evolve
(evolve)