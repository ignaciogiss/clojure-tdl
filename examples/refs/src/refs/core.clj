(ns refs.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args])
  
(def cuentaorigen (ref 100))
(def cuentadestino (ref 0))

; @cuenta1 o
;(deref cuenta1)



(defn transferencia [monto origen destino]
    (dosync
      (alter origen - monto)   ; alter from => (- @from amount)
      (alter destino   + monto)))  ; alter to   => (+ @to amount)

(println "montos originales") 
(println "cuenta origen:" @cuentaorigen) 
(println "cuenta destino:" @cuentadestino)

(transferencia 20 cuentaorigen cuentadestino)

(println "luego de la transaccion")

(println "cuenta origen:"@cuentaorigen)

(println "cuenta destino:"@cuentadestino)

  
  

    
    
  
  
  
