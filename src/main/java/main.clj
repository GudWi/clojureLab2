(ns main)

(defn is-small-seq [R i seq]
  (let [D (* 2 R),
        length-of-window (+ 1 D)]
      (if (< i R)
        (take (- length-of-window (- R i)) seq)
        (take length-of-window seq))))

  (def calculate-deviation (fn [x, y]
                     (* (- x y) (- x y))))

(defn dispersion
      ([seq R]
       (dispersion seq R 0))
      ([seq R i]
       (lazy-seq(cons (let [window (is-small-seq R i seq),
                            sum (reduce + window),
                            median (/ sum (count window)),
                            sum-of-deviations (reduce + (map calculate-deviation window (take (count window) (range median (+ median 1) 0)))),
                            disp (/ sum-of-deviations (count window))]
                           disp)
                      (dispersion (if (< i R) seq (next seq)) R (+ i 1))))))

(println (take 7(dispersion [1 2 3 4 5 6 7] 2)))
