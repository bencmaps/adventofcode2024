(require '[clojure.string :as str])

(defn read-input []
  (let [lines (->> "./input.txt" slurp str/split-lines)]
    (mapv (fn [line]
            (mapv parse-long (str/split line #"\s+")))
	  lines)))

(defn safe-report? [report]
  (let [level-deltas (mapv - report (rest report))]
    (or
      (every? #(<= 1 % 3) level-deltas)
      (every? #(<= -3 % -1) level-deltas))))

(defn pt2-safe-report?
  ([report] (pt2-safe-report? report 0))
  ([report allowed-unsafe]
   (let [report (vec report)
         safe-delta-asc? #(<= 1 % 3)
	 safe-delta-desc? #(<= -3 % -1)
	 safe? (some-fn safe-delta-asc? safe-delta-desc?)]
     (or
       (<= (count report) 1)
       (every? safe? (map - report (rest report)))
       (and
         (> allowed-unsafe 0)
         (loop [allowed-unsafe-remaining allowed-unsafe
                level-i 0]
           (cond
             (>= level-i
                 (count report)) true
	     (pt2-safe-report?
	       (into (subvec report 0 level-i) (subvec report level-i (inc level-i)))
	       (dec allowed-unsafe-remaining))
	     (recur (dec allowed-unsafe-remaining) (inc level-i))
	     true (recur allowed-unsafe-remaining (inc level-i)))))))))



;(defn pt2-safe-report?
;  ([report] (pt2-safe-report? report 0))
;  ([report allowed-unsafe]
;   (let [level-deltas (mapv - report (rest report))
;         safe-delta-asc? #(<= 1 % 3)
;	 safe-delta-desc? #(<= -3 % -1)
;	 safe-count-asc (count (filter safe-delta-asc? level-deltas))
;	 safe-count-desc (count (filter safe-delta-desc? level-deltas))]
;     (or
;       (<= (- (count level-deltas)
;              safe-count-asc)	
;	   allowed-unsafe)
;       (<= (- (count level-deltas)
;              safe-count-desc)	
;	   allowed-unsafe)))))

(defn solution1-safe-count [reports]
  (count
    (filter safe-report? reports)))

(defn solution2-safe-count [reports]
  (count
    (filter #(pt2-safe-report? % 1) reports)))

(defn main []
  (let [reports (read-input)]
    (println (solution1-safe-count reports))
    (println (solution2-safe-count reports))))

(main)
