(ns main
  (:require [clojure.core.async :as a]))

(def in (a/chan 10))
(def out (a/chan 10))
(def accumulated (set ()))

(defn fill-in-chan [] (take 10 [1 "func" 16 9 3 3 :reset "clojure" 5 3]))
(a/onto-chan in (fill-in-chan))

(defn fun [in out]
  (a/go-loop []
             (when-some [value (a/<! in)]
               (println "value" value)
               (when (not= value :reset)
                 (println accumulated)
                 (when (contains? accumulated value)
                   (println "founded in accumulated values"))
                 (when (not (contains? accumulated value))
                   (println "not found in accumulated values")
                   (def accumulated (conj accumulated value))
                   (a/>! out value))
                 )
               (when (= value :reset)
                 (println "reset")
                 (def accumulated (set nil))
                 )
               (recur))))

(fun in out)

(defn printer [channel channelName]
  (Thread/sleep 1000)
  (println "Class: " channelName)
  (a/go-loop []
    (when-some [value (a/<! channel)]
      (println "Val " value)
      (recur))))

(printer in "in")
(printer out "out")
