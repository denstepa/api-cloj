(defproject flock-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [org.postgresql/postgresql "9.3-1100-jdbc41"]
                 [korma "0.3.0-RC6"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.1.2"]
                 ]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler flock_api.handler/app, :port 5000}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}

  :repl-options {:prompt (fn [ns]
                         (str "\033[1;32m"
                              ns "=>"
                              "\033[0m "))}
  )
