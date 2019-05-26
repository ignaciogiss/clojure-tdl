(ns clojure-tdl.core
  (:gen-class))

(use '(incanter core datasets))

(defn -main
  [& args]
  (def iris (get-dataset :iris))
  (view iris))
