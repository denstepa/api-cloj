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


(defn get-all-sites [account_id]
  (select site
    (where {:account_id account_id})
    (fields :id :domain :title))
)



(defn find-by-token [token]
  (def s (first(select site
                 (where {:token token})
                 (limit 1)
                 (fields :id :domain :title :account_id))))
  (if (not (nil? s)) (s :id) 0 )
)


(defn get-all-campaigns [site_id]
  (select campaign
    (where {:site_id site_id})
    (fields :id :title :description))
)


(defn get-all-orders [site_id params]
  (select orders
    (where {:site_id site_id})
    (where {:parent_offer_id [not= nil]})
    (where {:campaign_id (if
                           (params "campaign_id")
                           (Integer/parseInt (params "campaign_id"))
                           [not= nil])})

    (fields :id :order_id :campaign_id :customer_id :price :order_time :confirmation_state))
)

;(contains? (set '("new" "confirmed" "unconfirmed")) (params "state"))

;(get-all-orders 8 {"state" "confirmed"})

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
