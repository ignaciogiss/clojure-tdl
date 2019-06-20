(ns clojure-tdl.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as s]
            [clojure.logistic :refer :all]
            [incanter.core :as i]
            ))


(defn cats []
  (let [data (matrix-dataset)
        ys (i/$ 0 data)
        xs (i/$ [:not 0] data)
        coefs (logistic-regression ys xs)
        classifier (comp logistic-class
                      (sigmoid-function coefs)
                      i/trans)]
    (println "Observed: " (map int (take 10 ys)))
    (println "Predicted:" (map classifier (take 10 xs)))))

(defn -main [& args]
    (cats))
