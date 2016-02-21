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

(defn create-user [user]
  (sql/insert! db :users user))

(defn create-comment [comment]
  (sql/insert! db :comments comment))

(defn get-all-users []
  (sql/query db ["select * from users"]))

(defn get-user-count []
  (sql/query db ["select count(id) as count from users"]))

(defn get-active-user [id]
  (sql/query db ["select * from users where id=? and active=1" id]))

(defn get-user [id]
  (sql/query db ["select * from users where id=?" id]))

(defn get-latest-entries [max]
  (sql/query db ["select entries.*, count(comments.entry) as comments from entries left join comments on (entries.id = comments.entry) group by entries.id order by entries.publishedDate desc limit ?" max]))

(defn get-entry [id]
  (sql/query db ["select * from entries where id=?" id]))

(defn get-comments [id]
  (sql/query db ["select * from comments where entry=?" id]))

(defn create-entry [entry]
  (sql/insert! db :entries entry))

(defn update-entry [id entry]
    (sql/update! db :entries ["id=?" id] entry))

(defn delete-entry [id]
   (sql/delete! db :entries ["id=?" id]))

(defn delete-user [id]
   (sql/delete! db :users ["id=?" id]))

(defn update-user [user]
  (sql/update! db :users ["id=?" (:id user)] user))