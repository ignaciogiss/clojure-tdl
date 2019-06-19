(ns clojure-tdl.core
  (:gen-class))

(use '(lambda-ml core))
(use '(lambda-ml nearest-neighbors))
(use '(lambda-ml distance))
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
  (->(get-dataset :iris)
     (to-matrix)
     (to-vect)))
;;Es lo mismo que 
;; (to-vect (to-matrix (get-dataset :iris)))
  
;;Devuelve un array de la forma
;; ( (prediccion-modelo-1-flor1, prediccion-modelo-1-flor-2 ...)
;;   (prediccion-modelo-2-flor1, prediccion-modelo2-flor2 ...) ...)
(defn predecir-con-modelos
  ([lista-modelos flores]
   (if-not (empty? lista-modelos) 
     (cons 
       (nearest-neighbors-predict (first lista-modelos) flores)
       (predecir-con-modelos (rest lista-modelos) flores))
     nil)))

(defn average [coll] 
  (/ (reduce + coll) (count coll)))

(defn transpose [coll]
   (apply map vector coll))

;;Devuelve una secuencias de array maps de la forma
;;({key1 cantidad-de-votos, key2 cantidad-de-votos ...}
;; {key1 cantidad-de-votos ...} ...
;; Cada array map es una flor
(defn obtener-votos [predicciones]
  (map frequencies (transpose (matrix 
                                (to-vect(flatten  predicciones))
                                (count (first predicciones))))))  

(defn maximo-array-map [array-map]
  (apply max-key #(val %) array-map))

(defn seleccionar-mas-votados [votos]
  (keys (map maximo-array-map votos)))

;;Dado un conjunto de predicciones. Si 2 modelos decidieron que es una flor 1
;;y 3 decidieron que es una flor 2, por mayoria devuelvo que es 2
;;La función recibe lo enviado por predecir con modelos
(defn seleccionar-prediccion-mas-frecuente
  [predicciones]
  (->
    (obtener-votos predicciones)
    (seleccionar-mas-votados)))

(defn numero-a-flor [array]
  (map {0.0 "virginica" 1.0 "versicolor" 2.0 "setosa"} array))

(defn -main
  [& args]
  (def iris-vec (obtener-iris-data-como-vector))
  (def modelos (crear-modelos-secuencialmente iris-vec))
  (def flores-para-predecir (take 150 (map butlast iris-vec)))
  (def predictions (predecir-con-modelos modelos flores-para-predecir))
  (println (numero-a-flor(seleccionar-prediccion-mas-frecuente predictions))))
