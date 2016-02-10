(ns my-app.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[my-app started successfully]=-"))
   :middleware identity})
