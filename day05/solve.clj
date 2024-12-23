(require '[clojure.string :as str])

(defn load-input []
  (let [lines (str/split-lines (slurp "input.txt"))
        [page-ordering-rules _ updates] (partition-by str/blank? lines)
	parse-rule (fn [s] (let [[required-page for-page]
	                         (map Integer/parseInt (str/split s #"\|"))]
			     {:page for-page :requires required-page}))
	parse-update (fn [s] (mapv Integer/parseInt (str/split s #",")))]
    {:rules (mapv parse-rule page-ordering-rules)
     :updates (mapv parse-update updates)}))

(defn mk-page->requires [rules]
  (->> rules
       (group-by :requires)
       (mapv (fn [[k vs]] [k (mapv :page vs)]))
       (into {})))

(defn valid-update? [upd]
  (or
    (<= 1 (count upd))
    ; foreach number (ordered) in the update, either:
    ;   ∆ it has no `requires`
    ;   ∆ or all of its `requires` are included before it, in this update
