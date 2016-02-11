(ns blog.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[blog started successfully]=-"))
   :middleware identity})
