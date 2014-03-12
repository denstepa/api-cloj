(ns flock_api.handler
    (:use compojure.core)
    (:use ring.util.response)
  ;  (:use korma.db)
    (:use korma.core)
   ; (:use flock_api.db)
    (:require [compojure.handler :as handler]
              ;[clojure.java.jdbc :as sql]
              [compojure.route :as route]
              [ring.middleware.json :as middleware]
          ;    [korma.db :as db]
           ;   [korma.core :as sql]
              [clojure.string :as str]
              [flock_api.db :as db]
              [flock_api.models.sites :as sites]
              ))



(defn check-access [params]
  (if
    (params "token")
    (sites/find-by-token (params "token"))
    0
  )
)


(defroutes app-routes
  (context "/api" {params :query-params} (defroutes api-routes
    (let [site_id (check-access params)]
      (if (>= site_id 5)

        (context "" [] (defroutes main-routes
        ;  (GET "/sites" [] ( response (sites/get-all-sites (isite :account_id) )))
          (GET "/campaigns" [] (response (sites/get-all-campaigns site_id)))

          (GET "/orders" [] ( response (sites/get-all-orders site_id params)))

          (GET "/" [] (response "Welcome!"))
          (route/not-found (str "Not Found for site:" site_id))
        ))
        (response "Invalid token")
      )
    )
     ; (context "/campaigns" [] (defroutes campaigns-routes
            ;(GET  "/" [] (sites/get-all-campaigns))
            ;(POST "/" {body :body} (create-new-document body))
            ;(context "/:id" [id] (defroutes sites-routes
            ;  (GET    "/" [] (get-sites id))
            ;  (PUT    "/" {body :body} (update-sites id body))
            ;  (DELETE "/" [] (delete-sites id)))))
            ;  )
           ; )
      ;      (route/not-found "Not Found")
       ;   )
      ;)
    ;)
  ))

  ;(GET "/api*" {params :query-params} (retreive params) )

  (GET "/" [] "Hello World")

  (route/resources "/")
  (route/not-found "Not Found")
  )

    (def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))
