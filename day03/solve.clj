(require '[clojure.string :as str])

(defn solution1 [src]
  (->> src
       (re-seq #"mul\((\d+),(\d+)\)")
       (map (fn [[_ a b]] (* (parse-long a) (parse-long b))))
       (apply +)))

(defn solution2 [src]
  (let [ops
        (->> src
             (re-seq #"(?:(mul)\((\d+),(\d+)\)|(do)\(\)|(don't)\(\))")
	     (map (fn [[_ mul-op mul-a mul-b do-op dont-op]]
	            {:op (keyword (or mul-op do-op dont-op))
		     :args (if mul-op [mul-a mul-b] nil)})))]
    (loop [acc 0
           mul-enabled? true
	   [op & remaining-ops] ops]
      (condp = (:op op)
        :don't (recur acc false remaining-ops)
	:do   (recur acc true  remaining-ops)
	:mul  (recur
	        (if mul-enabled?
	          (+ acc (apply * (map parse-long (:args op))))
	          acc)
		mul-enabled?
		remaining-ops)
	acc))))


(defn main []
  (let [src (slurp "./input.txt")]
    (println (solution1 src))
    (println (solution2 src))))

(main)
