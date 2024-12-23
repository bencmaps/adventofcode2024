(require '[clojure.string :as str])
(require '[clojure.core.reducers :as r])

(def input
  (->> (str/split "3 386358 86195 85 1267 3752457 0 741" #"\s+")
       (map parse-long)))

(defn blink-stone [^Long stone]
  (cond
    (zero? stone) [1]

    (even? (count (str stone)))
    (mapv #(parse-long (apply str %))
          (partition-all (/ (count (str stone)) 2)
	                 (str stone)))

    :else [(* stone 2024)]))

(defn blink [stones]
  (mapcat blink-stone stones))

(defn iterated-blink [stones]
  (if (> (count stones) 1000000)
      (r/fold 10000
        (r/mapcat blink-stone stones))
;      (apply concat
;        (pmap blink (partition-all 100000 stones)))
      (blink stones)))

(time (println (count (nth (iterate blink input) 33))))
(time (println (count (nth (iterate iterated-blink input) 33))))


;(dotimes [i 25]
;  (println (str i ": " (count (nth (iterate blink input) i)))))

(shutdown-agents)
