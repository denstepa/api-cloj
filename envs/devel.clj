{ :main-db {:classname "org.postgresql.Driver"
           :subprotocol "postgresql"
           :make-pool? true
           :subname (str "//10.19.0.23:5432/" "flocktory_production")
           :user "replica"
           :password "syphoceysHosh"}
; :zookeeper-endpoint "localhost:2181"
; :kafka-brokers-list ["localhost:9092"]
; :front {:port 8301}
; :logging {:stdout true}
}
