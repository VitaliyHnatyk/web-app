(ns blog.models.db
  (:require [clojure.java.jdbc :as sql]))

(defn db-url [host port database]
  (str "//" host ":" port "/" database))

(let [host "localhost"
      port 3306
      database "blog"
      user "root"
      password ""]
  (def db {:subprotocol "mysql"
           :subname     (db-url host port database)
           :user        user
           :password    password}))

(defn get-entry [id]
  (sql/query db ["select * from entries where id=?" id] :result-set-fn first))

(defn create-comment [comment]
  (sql/insert! db :comments comment))

(defn get-comments [id]
  (sql/query db ["select * from comments where entry=?" id] :result-set-fn first))