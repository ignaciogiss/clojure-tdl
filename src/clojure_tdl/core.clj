(ns clojure-tdl.core
  (:gen-class))

(defn average
  [numbers]
  (/ (apply + numbers) (count numbers)))

(defn intensive-calculation
  [paramater]
  (Thread/sleep 5000)
  (* paramater paramater))

(defn future-intensive-calculation
  [paramater]
  (future  (Thread/sleep 4000) (* paramater paramater)))
  
 
(defn -main
  [& args]
  (def futureA (future-intensive-calculation 2)) 
  (def futureB (future-intensive-calculation 5))
  (def futureC (future-intensive-calculation 10))
  (def futureD (future-intensive-calculation 25))
  (def futureE (future-intensive-calculation 100))
  (Thread/sleep 5000)
  (println @futureA)
  (println @futureB)
  (println @futureC)
  (println @futureD)
  (println @futureE))
