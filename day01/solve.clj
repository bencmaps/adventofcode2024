(require '[clojure.string :as str])

(def transpose
  (partial apply map vector))

(def sum
  (partial apply +))

(defn read-input []
  (let [line-pairs
        (->> "./input"
             slurp
             str/split-lines
             (map #(mapv parse-long (str/split % #"\s+"))))]
    (transpose line-pairs)))

(defn solution1-total-distance [list1 list2]
  (let [deltas (map (comp abs -)
		 (sort list1)
		 (sort list2))]
    (sum deltas)))

(defn solution2-similarity-score [list1 list2]
  (let [right-list-counts (frequencies list2)
        similarity-score-for-item (fn [left-item]
	                            (* left-item
				       (or (right-list-counts left-item) 0)))]
    (->> list1
         (map similarity-score-for-item)
	 sum)))

(defn main []
  (let [[list1 list2] (read-input)]
    (println (solution1-total-distance list1 list2))
    (println (solution2-similarity-score list1 list2))))

(main)
