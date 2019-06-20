(ns clojure-tdl.core-test
  (:require [clojure.test :refer :all]
            [clojure-tdl.core :refer :all]))

(deftest iris-as-vector-size-test
  (testing "Iris dataset as vector has 150 elements"
    (is (= (count (obtener-iris-data-como-vector)) 150))))
