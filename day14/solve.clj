(require '[clojure.string :as str])

(def tiles-x 101)
(def tiles-y 103)
(def quad-x (int (/ tiles-x 2)))
(def quad-y (int (/ tiles-y 2)))

(defn load-input []
  ; returns vec of robots
  (let [lines (str/split-lines (slurp "./input.txt"))
        parse-line (fn [s]
	             (let [[_ px py vx vy]
		           (->> s
			        (re-matches #"p=(\d+),(\d+) v=(-?\d+),(-?\d+)")
				(map parse-long))]
		       {:px px :py py
		        :vx vx :vy vy}))]
    (mapv parse-line lines)))

(defn advance-robot [{:keys [px py vx vy]}]
  {:vx vx :vy vy
   :px (mod (+ px vx) tiles-x)
   :py (mod (+ py vy) tiles-y)})

(defn quadrant-for [px py]
  (let [qx (cond (< px quad-x) 0
                 (> px quad-x) 1
		 :else nil)
	qy (cond (< py quad-y) 0
	         (> py quad-y) 1
		 :else nil)]
    [qx qy]))

(defn safety-factor [robots]
  (let [quadrant-freqs (frequencies (map #(quadrant-for (:px %) (:py %)) robots))]
    (apply *
      (map second
        (filter #(not-any? nil? (first %)) quadrant-freqs)))))

(println
  (safety-factor
    (nth (iterate #(mapv advance-robot %) (load-input))
      100)))
