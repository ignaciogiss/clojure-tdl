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

(defn -main
  [& args]
  (def iris (get-dataset :iris))
  ;(view iris)
  (def iris-vec (to-vect (to-matrix iris)))
  (def predictions (nearest-neighbors-predict (fit iris-vec 1) (map butlast iris-vec)))
  (view (seq predictions)))
 
