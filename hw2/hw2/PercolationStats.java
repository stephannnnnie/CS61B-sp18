package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private int time;
    private Percolation percolation;
    private int[] res;


    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N<=0||T<=0){
            throw new IllegalArgumentException();
        }
        this.time = T;
        res = new int[T];
        for (int j = 0; j < T; j++) {
            this.percolation = pf.make(N);
            for (int i = 0;i<N*N;i++){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation.open(row,col);
                if (percolation.percolates()){
                    break;
                }
            }
            int xt = percolation.numberOfOpenSites()/ (N* N);
            res[j]=xt;
        }

    }
    public double mean(){
//        int xt = 0;
//        for (int i = 0; i < time; i++) {
//            xt+= res[i];
//        }
        return StdStats.mean(res);
    }
    public double stddev(){
//        double dev = 0;
//        for (int i = 0; i < time; i++) {
//            dev+=Math.pow(res[i]-mean(),2);
//        }
//        return dev/(time-1);
        return StdStats.stddev(res);
    }

    public double confidenceLow(){
        return mean()-(1.96*stddev()/Math.sqrt(time));
    }

    public double confidenceHigh(){
        return mean()+(1.96*stddev()/Math.sqrt(time));
    }

}
