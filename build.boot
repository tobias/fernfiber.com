(set-env!
  :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                  [stasis "2.3.0"]
                  [hiccup "1.0.5"]]
  :resource-paths #{"src" "resources"})

(require '[fernfiber.site :refer [export]])

(deftask generate []
  (with-pre-wrap fileset
    (export)
    fileset))

