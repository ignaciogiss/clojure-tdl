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
  (future  (Thread/sleep 5000) (* paramater paramater)))
  
 
(defn -main
  [& args]
  (def parameters (seq(range 10)))
  (doseq [x parameters]  
    (println (future-intensive-calculation x))))
