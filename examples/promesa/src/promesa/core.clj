(ns promesa.core
  (:gen-class))

(defn -main
  
  [& args]
  ; defino una promesa de algo que se tiene que calcular en algun momento
  (def unapromesa (promise))
  
  ; voy a calcular la promesa, en este caso le asigno simplemente un valor
  (future
    (Thread/sleep 5000) 
    ;(deliver unapromesa 42) ; asigno un valor 
  ;  (deliver unapromesa ((fn [x] (* x x)) 10)) ; asigno el resultado de una funcion
    (deliver unapromesa (fn [x] (* x x))) ; asigno una funcion cuadrado
    
    (println "Promesa realizada!"))
  
  
  ; necesito el valor de la promesa, entonces lo pido desrefenciando
  ;y se queda bloqueado el hilo
  (println "Espero el resultado de la promesa")
  (println "Pregunto si esta realizada (la respuesta es false)")
  (println(realized? unapromesa))
  @unapromesa
  
  (println @unapromesa)
  (println "vuelvo a preguntar si esta realizada la respuesta es true")
  (println(realized? unapromesa))
  (println(@unapromesa 5)))
  
  


