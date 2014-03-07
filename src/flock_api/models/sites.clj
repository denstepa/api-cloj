(ns flock_api.models.sites
  ;  (:use korma.db)
    (:use korma.core)
    (:require [compojure.route :as route]
              [ring.middleware.json :as middleware]
              [clojure.string :as str]
              [flock_api.db :as db]
              ))

(declare site account campaign orders rewards)

(defentity site
  (database db/dev)
  (table :sites)
  (belongs-to account {:fk :account_id})
  ;(has-many campaigns)
)


(defn get-all-sites [isite]
  (select site
    (where {:account_id (isite :account_id)})
    (fields :id :domain :title))
)

(defn get-site [isite id]
  ( first(select site
    (with account)
    (where {:id (Integer/parseInt id)})
    (where {:account_id (isite :account_id)})
    (limit 1)
    (fields :id :domain :title :accounts.first_name :accounts.last_name :accounts.id))
  )
)


(defn find-by-token [token]
  (def s (select site
                 (where {:token token})
                 (limit 1)
                 (fields :id :domain :title :account_id)))
  (if (not (empty? s)) s )
)

(defn get-all-campaigns [isite]
  (select campaign
    (where {:site_id (isite :id)})
    (fields :id :title :description))
)

(defn get-all-orders [site params]
  (select orders
    (where {:site_id (site :id)})
    (where {:parent_offer_id [not= nil]})
    (fields :id :order_id :campaign_id :customer_id :price :order_time :confirmation_state))
)

(defentity account
  (database db/dev)
  (table :accounts)
  ;(has-many site)
)

(defentity campaign
  (database db/dev)
  (table :campaigns)
  (belongs-to site {:fk :site_id})
)

(defentity orders
  (database db/dev)
  (belongs-to site {:fk :site_id})
  (belongs-to campaign {:fk :campaign_id})
)

(defentity rewards
  (database db/dev)
  ;(belongs-to site {:fk :site_id})
  (belongs-to campaign {:fk :campaign_id})
)
