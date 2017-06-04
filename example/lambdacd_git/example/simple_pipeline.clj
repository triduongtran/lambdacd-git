(ns lambdacd-git.example.simple-pipeline
  (:use [compojure.core])
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd.steps.manualtrigger :refer [wait-for-manual-trigger]]
            [lambdacd.steps.control-flow :refer [either with-workspace in-parallel run]]
            [lambdacd.core :as lambdacd]
            [ring.server.standalone :as ring-server]
            [lambdacd.ui.ui-server :as ui]
            [lambdacd-git.core :as core]
            [lambdacd.runners :as runners]
            [clojure.java.io :as io]))

(defn wait-for-git [args ctx]
  (core/wait-for-git ctx (:repo-uri (:config ctx))
                     :ref "refs/heads/master"
                     :ms-between-polls (* 60 1000)))

(defn clone [args ctx]
  (core/clone ctx (:repo-uri (:config ctx)) (:revision args) (:cwd args)))

(defn ls [args ctx]
  (shell/bash ctx (:cwd args) "ls"))

(defn create-new-tag [args ctx]
  (if (:repo-pushable (:config ctx))
    (core/tag-version ctx (:cwd args) (:repo-uri (:config ctx)) "HEAD" (str (System/currentTimeMillis)))
    {:status :success
     :out    "SKIPPING, REPO IS NOT PUSHABLE"}))

(def pipeline-structure
  `((either
      wait-for-manual-trigger
      wait-for-git)
     (with-workspace
       clone
       core/list-changes
       ls
       create-new-tag)))

(defn -main [& args]
  (let [home-dir (io/file "/tmp/foo")
        config   {:home-dir      home-dir
                  :repo-uri      "git@github.com:flosell/testrepo"
                  :repo-pushable false}
        pipeline (lambdacd/assemble-pipeline pipeline-structure config)]
    (core/init-ssh!)
    (runners/start-one-run-after-another pipeline)
    (ring-server/serve (routes
                         (ui/ui-for pipeline)
                         (core/notifications-for pipeline))
                       {:open-browser? false
                        :port          8082})))
