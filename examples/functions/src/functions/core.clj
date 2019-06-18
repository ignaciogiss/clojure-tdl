(ns functions.core
  (:gen-class))

;;Devuelve una lista con todos los numeros desde el inicial hasta el final
(defn obtener-lista
  ([numero-inicial numero-final]
   (if (< numero-inicial numero-final)  
     (do (concat (vector numero-inicial) (obtener-lista (inc numero-inicial) numero-final)))
     (do (vector numero-inicial))))
  ([numero-final]
   (obtener-lista 0 numero-final)))

(defn -main
  []
  (obtener-lista 3))
