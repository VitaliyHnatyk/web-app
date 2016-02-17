(ns blog.routes.home
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [blog.models.db :as db]
            [noir.response :as resp]
            [noir.validation :as vali]))

(defn valid-comment? [title content name]
  (vali/rule (vali/has-value? title)
             [:title "title is required"])
  (vali/rule (vali/has-value? name)
             [:name "name is required"])
  (vali/rule (vali/has-value? content)
             [:content "content is required"])
  (vali/rule (vali/min-length? title 2)
             [:title "title must be at least 2 characters"])
  (vali/rule (vali/min-length? content 2)
             [:content "content must be at least 2 characters"])
  (not (vali/errors? :title :content :author)))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn country-page []
  (layout/render "about.html"))


(defn display-post [result & [comment]]
  (layout/render "entry.html"
                 (conj result
                         {:comments (db/get-comments (:id result))}
                         {:errors ((vali/has-value? :title) vali/get-errors [:title :content])}
                         {:comment comment}) ))

(defn handle-comment [id title content name]
  (let [comment {:entry id :title title :content content :name name}]
    (if (valid-comment? title content name)
      (do
        (db/create-comment comment)
        (resp/redirect (str "/post/" id)))
      (do (display-post (db/get-entry id) comment) ))))

(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/about/" [] (about-page))
           (GET "/country/" [] (country-page))
           (GET "/post/:id" [id] (display-post (db/get-entry id)))
           (POST "/post/:id" [id title content name]
             (handle-comment id title content name)))

