(ns flock_api.models
  ;  (:use korma.db)
    (:use korma.core)
    (:require [compojure.route :as route]
              [ring.middleware.json :as middleware]
              [clojure.string :as str]
              [flock_api.db :as db]
              ))


(defentity campaign
  (database db/dev)
  (belings-to site)
)
