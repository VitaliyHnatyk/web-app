(defproject blog "0.1.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [lib-noir "0.9.9"]
                 [compojure "1.4.0"]
                 [ring-server "0.4.0"]
                 [selmer "1.0.0"]
                 [com.taoensso/timbre "4.2.1"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.85"]
                 [environ "1.0.2"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [mysql/mysql-connector-java "5.1.36"]]

  :repl-options {:init-ns blog.repl}
  :plugins [[lein-ring "0.8.11"]
            [lein-environ "1.0.2"]]
  :ring {:handler blog.handler/app
         :init    blog.handler/init
         :destroy blog.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production {:ring {:open-browser? false
                       :stacktraces?  false
                       :auto-reload?  false}}
   :dev {:dependencies [[ring-mock "0.1.5"]
                        [ring/ring-devel "1.4.0"]]
         :env {:dev true}}}
  :min-lein-version "2.0.0")