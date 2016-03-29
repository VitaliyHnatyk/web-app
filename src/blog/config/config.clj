(ns blog.config.config
  (:require
    [clojurewerkz.propertied.properties :as p]
    [clojure.java.io :as io]))

(defn prop[]
(let [pl (p/load-from (io/file "resources/on/classpath.properties"))]
  (p/properties->map pl true)
  pl))