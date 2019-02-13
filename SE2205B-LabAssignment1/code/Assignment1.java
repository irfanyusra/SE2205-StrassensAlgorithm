
//Yusra Irfan -- yirfan3
//Phillip Hung Truong -- htruon

import java.io.*;
import java.util.Scanner;

public class Assignment1 {

	public int[][] denseMatrixMult(int[][] A, int[][] B, int size) {

		// no need to account for n not even or not 1 as we are assuming 2^n

		int[][] zeroMatrix = initMatrix(size);
		int[][] CMatrix = new int[size][size];
		// base case 
		if (size == 1)
			CMatrix[0][0] = A[0][0] * B[0][0];
		else {

			// Strassen’s Algorithm - 7 multiplication operations
			int[][] M0 = denseMatrixMult((sum(A, A, 0, 0, size / 2, size / 2, size / 2)),
					(sum(B, B, 0, 0, size / 2, size / 2, size / 2)), size / 2);

			int[][] M1 = denseMatrixMult((sum(A, A, size / 2, 0, size / 2, size / 2, size / 2)),
					(sum(B, zeroMatrix, 0, 0, 0, 0, size / 2)), size / 2);

			int[][] M2 = denseMatrixMult((sum(A, zeroMatrix, 0, 0, 0, 0, size / 2)),
					(sub(B, B, 0, size / 2, size / 2, size / 2, size / 2)), size / 2);

			int[][] M3 = denseMatrixMult((sum(A, zeroMatrix, size / 2, size / 2, 0, 0, size / 2)),
					(sub(B, B, size / 2, 0, 0, 0, size / 2)), size / 2);

			int[][] M4 = denseMatrixMult((sum(A, A, 0, 0, 0, size / 2, size / 2)),
					(sum(B, zeroMatrix, size / 2, size / 2, 0, 0, size / 2)), size / 2);

			int[][] M5 = denseMatrixMult((sub(A, A, size / 2, 0, 0, 0, size / 2)),
					(sum(B, B, 0, 0, 0, size / 2, size / 2)), size / 2);

			int[][] M6 = denseMatrixMult((sub(A, A, 0, size / 2, size / 2, size / 2, size / 2)),
					(sum(B, B, size / 2, 0, size / 2, size / 2, size / 2)), size / 2);

			// combining M matrices using addition and subtraction
			int[][] C00 = sum(sub((sum(M0, M3, 0, 0, 0, 0, size / 2)), M4, 0, 0, 0, 0, size / 2), M6, 0, 0, 0, 0,
					size / 2);

			int[][] C01 = sum(M2, M4, 0, 0, 0, 0, size / 2);

			int[][] C10 = sum(M1, M3, 0, 0, 0, 0, size / 2);

			int[][] C11 = sum(sub(sum(M0, M2, 0, 0, 0, 0, size / 2), M1, 0, 0, 0, 0, size / 2), M5, 0, 0, 0, 0,
					size / 2);

			// these can be combined to obtain C0,0, C0,1, C1,0, C1,1.
			// This requires two nested loops (to add columns and rows of matrices).

			for (int i = 0; i < C00.length; i++) {
				for (int j = 0; j < C00.length; j++) {
					CMatrix[i][j] = C00[i][j];
					CMatrix[i][(size / 2) + j] = C01[i][j];
					CMatrix[(size / 2) + i][j] = C10[i][j];
					CMatrix[(size / 2) + i][(size / 2) + j] = C11[i][j];
				}
			}

		}
		return CMatrix;
	}

	// function to sum the matrix
	public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
		int[][] array = new int[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++)
				array[x][y] = A[x1 + x][y1 + y] + B[x2 + x][y2 + y];
		}
		return array;
	}

	// function to subtract the matrix
	public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
		int[][] array = new int[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++)
				array[x][y] = A[x1 + x][y1 + y] - B[x2 + x][y2 + y];
		}
		return array;

	}

	// function to initialize matrix
	public int[][] initMatrix(int n) {
		// 2d array of n size will be initialized
		int[][] array = new int[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++)
				array[x][y] = 0;
		}
		return array;
	}

	// prints out the matrix
	public void printMatrix(int n, int[][] A) {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				System.out.print(A[x][y] + " ");
			}
			System.out.println();
		}
	}

	// readMatrix
	public int[][] readMatrix(String filename, int n) throws Exception {
		Scanner scannedFile = new Scanner(new BufferedReader(new FileReader("../" + filename)));
		
		int[][] A = new int[n][n];
		while (scannedFile.hasNextLine()) {
			for (int x = 0; x < n; x++) {
				String[] fileLine = scannedFile.nextLine().trim().split(" ");
				for (int y = 0; y < fileLine.length; y++) {
					int matrixVal = Integer.parseInt(fileLine[y]);
					A[x][y] = matrixVal;
				}
			}
		}
		return A;
	}

}
