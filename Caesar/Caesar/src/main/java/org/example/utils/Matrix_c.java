package org.example.utils;

public class Matrix_c {
    public int[][] matrix;
    public int[][] matrixToExchange;
    public int len;

    public void setValue(int[][] matrix) {
        this.matrix = matrix;
        this.len = this.matrix.length;
        this.matrixToExchange = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.matrixToExchange[i][j] = matrix[i][j];
            }
        }
    }
    /*
    求行列式的值，判断是否为满秩(Getvalue)
     */
    public Integer Getvalue(int[][] m){
        int n = m.length;
        if (n == 1)                                                      // 如果是一阶行列式,直接返回该元素
            return m[0][0];
        if (n==2){
            return m[n-1][n-1]*m[n-2][n-2] - m[n-1][n-2]*m[n-2][n-1];   // 如果是二阶行列式,计算后返回
        }else{                                                       // 如果是三阶或更多，递归查找计算其第一行元素的代数余子式
            int sum = 0;
            for (int g = 0;g<n ; g++) {
                if (m[0][g]!=0) {                                      //不等于零才进行，有时会快很多
                    sum += m[0][g] * Getvalue(GetAlgebraicComplement(0,g, m)) * Math.pow(-1, g);
                }
            }
            System.out.println("sum = " + sum);
            return sum;
        }
    }

    /*
    矩阵乘法(MultplyMatrix)
     */
    public int[][] MultplyMatrix(int[][] leftMatrix) {
        int[][] result = new int[leftMatrix.length][this.len];
        for (int i = 0; i < leftMatrix.length; i++) {
            for (int j = 0; j < this.len; j++) {
                result[i][j] = 0;
            }
        }
        for (int i = 0; i < leftMatrix.length; i++) {
            for (int k = 0; k < this.len; k++) {
                int sum = 0;
                for (int j = 0; j < this.len; j++) {
                    sum += leftMatrix[i][j]*this.matrix[j][k];
                }
                result[i][k] = sum;
            }
        }
        return result;
    }

    //矩阵乘数字
    public int[][] MultplyMatrixAndNum(int num,int[][] m) {
        int[][] matrix = new int[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                matrix[i][j] = m[i][j] * num;
            }
        }
        return matrix;
    }

    /*
    求代数余子式(GetAlgebraicComplement)
     */
    public int[][] GetAlgebraicComplement(int row, int col,int[][] m){
        int[][] n = new int[m.length-1][m.length-1];
        for (int i = 0; i < row;i++){
            for (int j = 0; j < col; j++){
                n[i][j] = m[i][j];
            }
            for (int j = col; j < m.length-1; j++){
                n[i][j] = m[i][j+1];
            }
        }
        for (int i = row; i < m.length-1;i++){
            for (int j = 0; j < col; j++){
                n[i][j] = m[i+1][j];
            }
            for (int j = col; j < m.length-1; j++){
                n[i][j] = m[i+1][j+1];
            }
        }
        return n;
    }
    /*
    求逆矩阵(getInversionMatrix)
     */
    public int[][] getInversionMatrix(int[][] m){
        int lengthOfM = m.length;
        int[][] InversionMatrix = getAdjointMatrix(m);
        int value = Getvalue(m);
        for (int i = 0; i < lengthOfM; i++){
            for (int j = 0; j < lengthOfM; j++){
                InversionMatrix[i][j] /= value;
            }
        }
        return InversionMatrix;
    }
    /*
    求伴随矩阵
     */
    public int[][] getAdjointMatrix(int[][] m){
        int lengthOfM = m.length;
        int[][] AdjoinMatrix = new int[lengthOfM][lengthOfM];
        for (int i = 0; i < lengthOfM; i++){
            for (int j = 0; j < lengthOfM; j++){
                AdjoinMatrix[j][i] = Getvalue(GetAlgebraicComplement(i,j,m))*(int)Math.pow(-1,i+j+2);
            }
        }
        return AdjoinMatrix;
    }

}