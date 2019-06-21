(defproject clojure-tdl "0.1.0-SNAPSHOT"
  :description "Examples of clojure machine learning with Incanter"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [incanter "1.9.3"]
                 [metosin/compojure-api "1.1.11"]
                 [lambda-ml "0.1.1"]]
   :ring {:handler clojure-tdl.handler/app}
  :main ^:skip-aot clojure-tdl.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins [[lein-ring "0.12.5"]]}})


