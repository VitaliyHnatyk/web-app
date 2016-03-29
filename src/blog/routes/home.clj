(ns blog.routes.home
  (:use compojure.core)
  (:require [blog.views.layout :as layout]
            [compojure.route :as route]
            [blog.config.config :as prop]
            [blog.models.db :as db]
            [blog.util :as util]
            [noir.session :as session]
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

(defn prop []
  '(prop/prop))

(defn home-page []
  (layout/render "home.html"
                 (merge prop
                       {:entries (db/get-latest-entries 10)}) )
  println :property)

(defn about-page []
  (layout/render "about.html"))

(defn display-post [entry & [comment]]
  (layout/render "entry.html"
                 (conj entry
                       {:comments (db/get-comments (:id entry))}
                       {:errors (vali/get-errors (:title :content))}
                       {:comment comment})))

(defn handle-comment [id title content name]
  (let [comment {:entry id :title title :content content :name name}]
    (if (valid-comment? title content name)
      (do
        (db/create-comment comment)
        (resp/redirect (str "/post/" id)))
      (display-post (db/get-entry id) comment))))

(defroutes home-routes (GET "" [] (home-page))
           (GET "/" [] (home-page))
           (GET "/about" [] (about-page))
           (GET "/post/:id" [id perma]
             (display-post (db/get-entry id)))
           (POST "/post/:id" [id title content name]
             (handle-comment id title content name))
           (route/resources "/")
           (route/not-found "<h1>Page not found</h1>")
           )


;cache use from https://github.com/clojure/core.cache