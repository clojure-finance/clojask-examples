(defproject clojask-examples "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.github.clojure-finance/clojask "1.2.2"]
                 [org.clojure/core.async "0.4.500"]]
  :main ^:skip-aot clojask-examples.core/foo
  :repl-options {:init-ns clojask-examples.core  ;; change the example you want to run here
                 :timeout 180000})
