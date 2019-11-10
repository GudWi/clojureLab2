(ns main)

(def seq1 (lazy-seq (iterate inc 1)))

(defn dispersion [window-size, seq]
  (loop [i 0]
    (if (< i 5)
      (do
        (println "i =" i)
        (let [ar (take window-size (drop (* window-size i) seq)),
              sum (loop [j 0, n 0]
                    (if (< j window-size)
                      (do (recur (inc j) (+ n (first (take 1 (drop j ar))))))
                      n)),
              sum-of-deviations (loop [j 0, n 0]
                                  (if (< j window-size)
                                    (do (recur (inc j) (+ n (* (- (first (take 1 (drop j ar))) (/ sum window-size))
                                                               (- (first (take 1 (drop j ar))) (/ sum window-size))))))
                                    n))]
          (println ar)
          (println "sum =" sum)
          (println "sum-of-deviations =" sum-of-deviations)
          (println "дисперсия =" (/ sum-of-deviations window-size))
          )
        (recur (inc i))
        )
      )
    )
)

(dispersion 3 seq1)
