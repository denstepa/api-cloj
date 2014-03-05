(ns flock_api.db
    (:use korma.db)
    (:require
              [clojure.string :as str]
              )
)

(def dev (create-db (postgres {:db "flocktory"
                                  :username "flocktory"
                                  :password "flocktory"
                                  :host "localhost"})))
