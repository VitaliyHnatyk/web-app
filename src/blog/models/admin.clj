(ns blog.models.admin
  (:require [clojure.java.jdbc :as sql]
            [blog.models.db :as db]))

(defn get-latest-entries [max]
  (sql/query db/db ["select entries.*, count(comments.entry) as comments from entries left join comments on (entries.id = comments.entry) group by entries.id order by entries.publishedDate desc limit ?" max] ))

(defn get-all-users []
  (sql/query db/db ["select * from users"]))

(defn get-user [id]
 (sql/query db/db ["select * from users where id=?" id] ))

(defn create-entry [entry]
   (sql/insert! db/db :entries entry))

(defn update-entry [id entry]
  (sql/update! db/db :entries ["id=?" id] entry))

(defn delete-entry [id]
   (sql/delete! db/db :entries ["id=?" id]))

(defn delete-user [id]
   (sql/delete! db/db :users ["id=?" id]))

(defn update-user [user]
  (sql/update! db/db :users ["id=?" (:id user)] user))