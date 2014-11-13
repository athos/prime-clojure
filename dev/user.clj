(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [clojure.repl :refer :all]
            [clojure.pprint :refer [pp pprint]]
            [clojure.tools.analyzer.jvm :as a]
            [clojure.tools.emitter.jvm :as e]
            [clojure.tools.analyzer :as ana]
            [clojure.tools.analyzer.env :as env]
            [prime-clojure.core :refer :all]))

(defn test [form]
  (with-bindings {#'ana/macroexpand-1 a/macroexpand-1
                  #'ana/create-var    a/create-var
                  #'ana/parse         a/parse
                  #'ana/var?          var?}
    (env/ensure (a/global-env)
      (env/with-env (swap! env/*env* merge {:passes-opts #{}})
        (ana/analyze form (a/empty-env))))))
