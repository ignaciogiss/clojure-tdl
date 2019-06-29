(ns clojure-tdl.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as s]
            [clojure.java.io :as io]
            [incanter.io :as iio]
            [clojure-tdl.logistic :refer :all]
            [incanter.core :as i]
            [mikera.image.core :as m]
            ))
;;(def cat (m/load-image-resource "my_cat.png"))
(def nasa (m/load-image-resource "nasa.png"))

(defn load-data [file]
  (-> (io/resource file)
      (str)
      (iio/read-dataset :delim \tab :header true)))

(defn add-dummy [column-name from-column value dataset]
  (i/add-derived-column column-name
                        [from-column]
                        #(if (= % value) 1 0)
                        dataset))

(defn matrix-dataset-train []
  (->> (load-data "train.tsv")
       (i/to-matrix)))

(defn matrix-dataset-train_y []
 (->> (load-data "train_y.tsv")
      (i/to-matrix)))

(defn cats []
  (let [data (matrix-dataset-train)
        data_y (matrix-dataset-train_y)
        ys (i/$ 0 data_y)
        xs (i/$ [:not 0] data)
        coefs (logistic-regression ys xs)
        classifier (comp logistic-class
                      (sigmoid-function coefs)
                      i/trans)]
    (println "Observed: " (map int (take 10 ys)))
    (println "Predicted:" (map classifier (take 10 xs)))))

(defn -main [& args]
    ;;(m/show nasa)
    (println (sigmoid-function (int-array [0 9.2]))
    ))
