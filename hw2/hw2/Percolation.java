package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation extends WeightedQuickUnionUF{
    int[][] system;
    int N;
    int num;
    public Percolation(int N){
        super(N*N);
        this.N = N;
        if (N<=0){
            throw new IllegalArgumentException();
        }

        system = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                system[i][j]=0;
                //initially blocked
            }
        }
    }
    public void open(int row, int col){
        if (row>=N||row<0||col>=N||col<0){
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row,col)){
            system[row][col]=1;
            num++;
        }

        if (row>0&&isOpen(row-1,col)){
            union((row-1)*N+col,row*N+col);
        }
        if (row<N-1&&isOpen(row+1,col)){
            union((row+1)*N+col,row*N+col);
        }
        if (col>0&&isOpen(row,col-1)){
            union(row*N+col,row*N+col-1);
        }
        if (col<N-1&&isOpen(row,col+1)){
            union(row*N+col,row*N+col+1);
        }


    }
    public boolean isOpen(int row, int col){
        if (row>=N||row<0||col>=N||col<0){
            throw new IndexOutOfBoundsException();
        }
        return system[row][col] == 1;
    }
    public boolean isFull(int row, int col){
        if (row>=N||row<0||col>=N||col<0){
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row,col)) {
            for (int i = 0; i < N; i++) {
                if (connected(row * N + col, i)) {
                    return true;
                }
            }
        }
        return false;
    }
    public int numberOfOpenSites(){
        return num;
    }
    public boolean percolates(){
        for (int i = 0; i < N; i++) {
            if (isFull(N-1,i)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }

}
