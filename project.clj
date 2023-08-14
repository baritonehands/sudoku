(defproject sudoku "0.1.0"
  :description "Clojure Sudoku Generator"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot sudoku.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
