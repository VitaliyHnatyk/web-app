(defproject blog "0.1.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [lib-noir "0.9.4"]
                 [compojure "1.1.6"]
                 [ring-server "0.4.0"]
                 [selmer "0.6.6"]
                 [com.taoensso/timbre "3.1.6"]
                 [com.taoensso/tower "2.0.2"]
                 [markdown-clj "0.9.43"]
                 [environ "0.5.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [cljsjs/react "0.14.3-0"]
                 [cljsjs/react-dom "0.14.3-1"]
                 [org.omcljs/om "0.9.0"]
                 [clojurewerkz/propertied "1.2.0"]
                 [org.clojure/core.cache "0.6.4"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [mysql/mysql-connector-java "5.1.36"]]

  :repl-options {:init-ns blog.repl}
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.0.3"]
            [lein-asset-minifier "0.2.0"]
            [deraen/lein-less4j "0.5.0"]
            [lein-cascade "0.1.2"]
            [org.slf4j/slf4j-nop "1.6.4"]
            [lein-environ "0.5.0"]]

  :clean-targets ^{:protect false} ["resources/public/css" "out-adv" "app.js"]

  :ring {:handler blog.handler/app
         :init    blog.handler/init
         :destroy blog.handler/destroy}


  :cljsbuild {:builds [
                       {:id           "dev"
                        :source-paths ["srccljs"]
                        :compiler
                                      {
                                       :main blog.main
                                       :optimizations :none
                                       :output-to     "resources/public/js/app.js"
                                       :output-dir    "resources/public/js/"
                                       :pretty-print  true
                                       :source-map    true}}
                       {:id           "release"
                        :source-paths ["srccljs"]
                        :compiler
                                      {:main blog.main
                                       :output-to        "resources/public/js/app.js"
                                       :optimizations    :advanced
                                       :pretty-print     false
                                       :output-wrapper   false
                                       :externs          ["externs/vendor.js"]
                                       :closure-warnings {:non-standard-jsdoc :off}}}]}




  :less {:source-paths ["src_assets/less"]
         :target-path  "resources/public/css"
         :source-map   true
         :compression  true
         :debug        true}


  :minify-assets {:assets
                  {"resources/public/css/site.min.css"
                   "resources/public/css/screen.css"}
                  }


  :cascade {"generate" [
                        ["clean"]
                        ["less4j" "once"]
                        ]
            "bar"      ["generate"
                        ["javac"]]
            "baz"      ["bar"
                        ["test"]]
            "quux"     ["bar"                               ; triggers "bar"
                        ["doc"]                             ; codox plugin example
                        ["uberjar"]]}


  :profiles {:uberjar    {:aot :all}
             :production {:ring {:open-browser? false
                                 :stacktraces?  false
                                 :auto-reload?  false}}
             :dev        {:dependencies [[ring-mock "0.1.5"]
                                         [ring/ring-devel "1.2.2"]]
                          :env          {:dev true}}}
  :min-lein-version "2.0.0"
  )
