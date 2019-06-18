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
      modelo5 (fit data 5)
      modelo6 (fit data 6)
      modelo7 (fit data 7)
      modelo8 (fit data 8)
      modelo9 (fit data 9)
      modelo10 (fit data 10)]
    into-array[modelo1 modelo2 modelo3 modelo4 modelo5]))

(defn crear-modelos-concurrentemente
    [data]
  (let
     [modelo1 (future(fit data 1))
      modelo2 (future(fit data 2))
      modelo3 (future(fit data 3))
      modelo4 (future(fit data 4))
      modelo5 (future(fit data 5))
      modelo6 (future(fit data 6))
      modelo7 (future(fit data 7))
      modelo8 (future(fit data 8))
      modelo9 (future(fit data 9))
      modelo10 (future(fit data 10))]
    into-array[@modelo1 @modelo2 @modelo3 @modelo4 @modelo5]))
  

(defn obtener-iris-data-como-vector
  []
  (def iris (get-dataset :iris))
  (to-vect (to-matrix iris)))

;;Devuelve un array de la forma
;; ( (prediccion-modelo-1-flor1, prediccion-modelo-1-flor-2 ...)
;;   (prediccion-modelo-2-flor1, prediccion-modelo2-flor2 ...) ...)
(defn predecir-con-modelos
  ([lista-modelos flores lista-predicciones]
   (if-not (empty? lista-modelos) 
     (predecir-con-modelos
       (rest lista-modelos)
       flores
       (cons(nearest-neighbors-predict (first lista-modelos) flores)
         lista-predicciones))
     ;;else
     lista-predicciones))
  ([lista-modelos flores]
   (predecir-con-modelos lista-modelos flores nil)))

(defn -main
  [& args
   (def iris-vec (obtener-iris-data-como-vector))]
  (def modelos (crear-modelos-secuencialmente iris-vec))
  (def flores-para-predecir (take-last 3 (map butlast iris-vec)))
  (def predictions (predecir-con-modelos modelos flores-para-predecir))
  (println predictions))
