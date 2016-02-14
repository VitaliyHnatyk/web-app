(ns blog.models.admin
  (:require [clojure.java.jdbc :as sql]
            [blog.models.db :as db]))

(defn get-latest-entries [max]
  (sql/with-connection db/db
                       (sql/with-query-results
                         res ["select entries.*, count(comments.entry) as comments from entries left join comments on (entries.id = comments.entry) group by entries.id order by entries.publishedDate desc limit ?" max] (doall res))))

(defn get-all-users []
  (sql/with-connection db/db
                       (sql/with-query-results
                         res ["select * from users"] (doall res))))
(defn get-user [id]
  (sql/with-connection db/db
                       (sql/with-query-results
                         res ["select * from users where id=?" id] (first res))))

(defn create-entry [entry]
  (sql/with-connection db/db (sql/insert-record :entries entry)))

(defn update-entry [id entry]
  (sql/with-connection db/db (sql/update-values :entries ["id=?" id] entry)))

(defn delete-entry [id]
  (sql/with-connection db/db (sql/delete-rows :entries ["id=?" id])))

(defn delete-user [id]
  (sql/with-connection db/db (sql/delete-rows :users ["id=?" id])))

(defn update-user [user]
  (sql/with-connection db/db (sql/update-values :users ["id=?" (:id user)] user)))