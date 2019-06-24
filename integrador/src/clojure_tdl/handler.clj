(ns clojure-tdl.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [clojure-tdl.core :refer :all]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Api TP Integrador"
                    :description "Compojure Api para TP Integrador"}
             :tags [{:name "Api Iris"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/adivinar-flor" []
        :return {:result String}
        :query-params [largo-sepalo :- Double, ancho-sepalo :- Double,
                       largo-petalo :- Double, ancho-petalo :- Double]
        :summary "Adivina el tipo de flor"
        (ok {:result (do
                       (def modelos
                        (-> (obtener-iris-data-como-vector)
                            (crear-modelos-secuencialmente)))
                       (def flores-a-predecir
                         [[largo-sepalo, ancho-sepalo,largo-petalo, ancho-petalo]])
                       (def predicciones 
                         (seleccionar-prediccion-mas-frecuente
                           (predecir-con-modelos modelos flores-a-predecir)))
                       (def predicciones-texto (numero-a-flor predicciones))
                       (first predicciones-texto))})))))
                       
