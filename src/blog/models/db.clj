(ns blog.models.db
  (:require [clojure.java.jdbc :as sql]))

(defn db-url [host port database]
  (str "//" host ":" port "/" database))

(let [host "localhost"
      port 3306
      database "blog"
      user "root"
      password ""]
  (def db {:classname "com.mysql.jdbc.Driver"
           :subprotocol "mysql"
           :subname (db-url host port database)
           :user user
           :password password}))

(defn get-entry [id]
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from entries where id=?" id] (first res))))

(defn create-comment [comment]
  (sql/with-connection db (sql/insert-record :comments comment)))

(defn get-comments [id]
  (sql/with-connection db
                       (sql/with-query-results
                         res ["select * from comments where entry=?" id] (doall res))))