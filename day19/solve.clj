(require '[clojure.string :as str])

(defn load-input []
  (let [lines (->> "./input.txt" slurp str/split-lines)]
    {:available-patterns (str/split (first lines) #",\s*")
     :desired-designs (rest (rest lines))}))


(defn find-solution [pats desired-design]
  ; returns ordered vec of pats accomplishing the desired-design
  (let [prefix-candidates
        (filterv #(str/starts-with? desired-design %))

	remaining-portions
	(mapv #(- (count desired-design) (count %)) prefix-candidates)]
    (if-let [answer
             (first
               (filter #(zero? (- (count desired-design) (count %)))
	               prefix-candidates))]
      [answer]


    (if-let [
      (some (fn [prefix-pat] ..) prefix-candidates)
    (first
      (map (fn [prefix-pat]
             (find-solution pats


(defn solution1 [{pats :available-patterns
                  desired-designs :desired-designs}]
  (let [possibles]))


(defn main []
  (let [input (load-input)]
    (println input)))

;(main)
