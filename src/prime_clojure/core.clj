(ns prime-clojure.core
  (:require [clojure.tools.analyzer.jvm :refer [parse specials]]
            [clojure.tools.emitter.jvm.emit :refer [-emit]]))

(alter-var-root #'specials conj 'asm)

(defmethod parse 'asm [form env]
  {:op :asm
   :env env
   :form form
   :children []
   :insns (vec (for [[op & args] (rest form)]
                 `[~(keyword (name op)) ~@args]))})

(defmethod -emit :asm [{:keys [insns]} frame]
  insns)
