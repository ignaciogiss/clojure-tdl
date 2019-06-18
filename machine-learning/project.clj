(defproject clojure-tdl "0.1.0-SNAPSHOT"
  :description "Examples of clojure machine learning with Incanter"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [incanter "1.9.3"]
                 [lambda-ml "0.1.1"]]
  :main ^:skip-aot clojure-tdl.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
