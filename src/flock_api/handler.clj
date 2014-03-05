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
    (first (sites/find-by-token (params "token")))
  )
)

(defn retreive [params]
  (def r
      (if
        (params "token")
        (sites/find-by-token (params "token") )
        false))
  (response
    (if r r (str "Invalid token " params) )
  )
)


(defroutes app-routes


  (context "/api2" [] (defroutes api-routes
    (context "/*" {params :query-params} (def access_site (check-access params)) )
    (if (not (= access_site nil))
      ;(
      (context "/sites" [] (defroutes sites-routes
            (GET  "/" [] ( response (sites/get-all-sites access_site)))
            (context "/:id" [id] (defroutes sites-routes
              (def site (sites/get-site access_site id))
              (GET    "/campaigns" [] (response (sites/get-all-campaigns site)))
              (GET    "/" [] (response site))
            ;  (PUT    "/" {body :body} (update-sites id body))
            ;  (DELETE "/" [] (delete-sites id)))))
             ))

            (route/not-found "Not Found")
          )
      )

      (response "Invalid token")
    )
      (context "/campaigns" [] (defroutes campaigns-routes
            ;(GET  "/" [] (sites/get-all-campaigns))
            ;(POST "/" {body :body} (create-new-document body))
            ;(context "/:id" [id] (defroutes sites-routes
            ;  (GET    "/" [] (get-sites id))
            ;  (PUT    "/" {body :body} (update-sites id body))
            ;  (DELETE "/" [] (delete-sites id)))))
            ;  )
           ; )
            (route/not-found "Not Found")
          )
      )
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
