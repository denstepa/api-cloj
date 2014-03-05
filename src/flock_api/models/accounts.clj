(ns flock_api.models
  ;  (:use korma.db)
    (:use korma.core)
    (:require [compojure.route :as route]
              [ring.middleware.json :as middleware]
              [clojure.string :as str]
              [flock_api.db :as db]
              ))


(defentity account
  (database db/dev)
  (has-many flock_api.models/sites)
)

(defn find-by-token [token]
  (def s (select accounts
            (with flock_api.models.sites/sites
              (where {:token token})
              (limit 1)
              (fields :id :domain :title :account_id)))
  )
  (if (not (empty? s)) s )
)
