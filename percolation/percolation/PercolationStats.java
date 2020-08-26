/* *****************************************************************************
 *  Name:    LEE CHINGYEN
 *  NetID:   B06602035
 *  Precept: P00
 *
 *  Description:  Perform a series of computational experiments.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int num; // record n
    private int t; // record trials
    private double[] threshold; // the consuming time of every trial

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Illegal argument!!");

        num = n;
        t = trials;
        threshold = new double[trials];
        Percolation p;

        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);

            while (!p.percolates()) {
                // open a random site until grid is percolates.
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (!p.isOpen(row, col)) p.open(row, col);
            }
            // record consuming time
            threshold[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return (mean() - (1.96 * stddev() / Math.sqrt(t)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return (mean() + (1.96 * stddev() / Math.sqrt(t)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        Stopwatch w = new Stopwatch();
        PercolationStats p = new PercolationStats(N, T);
        double time = w.elapsedTime();

        System.out.print("mean()            = " + p.mean() + "\n");
        System.out.print("stddev()          = " + p.stddev() + "\n");
        System.out.print("confidenceLow()   = " + p.confidenceLow() + "\n");
        System.out.print("confidenceHigh()  = " + p.confidenceHigh() + "\n");
        System.out.print("elapsed time      = " + time + "\n");
    }

}
