(ns clojure-tdl.core
  (:gen-class))

(use '(lambda-ml core))
(use '(lambda-ml nearest-neighbors))
(use '(lambda-ml distance))
(use '(ultra-csv core))
(use '(incanter core datasets))

(defn fit
  [data k]
  (->(make-nearest-neighbors-regressor k lambda-ml.distance/euclidean)
     (nearest-neighbors-fit data)))


(defn crear-modelos-secuencialmente
     [data]
  (let
     [modelo1 (fit data 1)
      modelo2 (fit data 2)
      modelo3 (fit data 3)
      modelo4 (fit data 4)
      modelo5 (fit data 5)]
    into-array[modelo1 modelo2 modelo3 modelo4 modelo5]))

(defn crear-modelos-concurrentemente
    [data]
  (let
     [modelo1 (future(fit data 1))
      modelo2 (future(fit data 2))
      modelo3 (future(fit data 3))
      modelo4 (future(fit data 4))
      modelo5 (future(fit data 5))]
    into-array[@modelo1 @modelo2 @modelo3 @modelo4 @modelo5]))
  

(defn obtener-iris-data-como-vector
  []
  (def iris (get-dataset :iris))
  (to-vect (to-matrix iris)))

(defn -main
  [& args]
  (def iris-vec (obtener-iris-data-como-vector))
  (def modelos (crear-modelos-secuencialmente iris-vec))
  (def predictions (nearest-neighbors-predict (first modelos) (map butlast iris-vec)))
  (view (seq predictions)))
