(ns my-app.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [my-app.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[my-app started successfully using the development profile]=-"))
   :middleware wrap-dev})
