(ns user
  (:require [clojure.tools.nrepl.server :as nrepl]
            [clojure.core.async :as async :refer [go <!! >!! <! >!]]))

(defn http-call [url]
  (go
    (<! (async/timeout (* 500 (count url))))
    url))

(defn get-fastest [urls]
  (<!! (go (async/alts! (map http-call urls)))))


(defn slow [f t]
  (fn [& args]
    (Thread/sleep t)
    (apply f args)))

(defn foo [v]
  (println "entering foo at" v)
  (Thread/sleep 5000)
  (let [val (inc v)]
    (println "returing" val)
    val))

(defn produce [])
