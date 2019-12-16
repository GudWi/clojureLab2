(ns main)

(defn is-small-seq? [R i seq]
      (if (< i R) (take (- (+ 1 (* 2 R)) (- R i)) (drop (- i R) seq)) (take (+ 1 (* 2 R)) (drop (- i R) seq))))

  (def my-function (fn [x, y]
                     (* (- x y) (- x y))))

(defn dispersion
      ([seq R]
       (dispersion seq R 0))
      ([seq R i]
       (lazy-seq(cons (let [window (is-small-seq? R i seq),
                            sum (reduce + window),
                            median (/ sum (count window)),
                            sum-of-deviations (reduce + (map my-function window (take (count window) (range median (+ median 1) 0)))),
                            disp (/ sum-of-deviations (count window))]
                           disp)
                      (dispersion seq R (+ i 1))))))

(defn fib-seq
      ([]
       (fib-seq 0 1))
      ([a b]
       (lazy-seq
         (cons b (fib-seq b (+ a b))))))

(println(take 5 (fib-seq)))
(println (take 7(dispersion [1 2 3 4 5 6 7] 2)))
