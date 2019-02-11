import java.io.*;
import java.util.Scanner;

public class Assignment1 {

	public int[][] denseMatrixMult(int[][] A, int[][] B, int size) {

		// no need to account for n not even or not 1 as we are assuming 2^n

		int[][] zeroMatrix = initMatrix(size);
		int[][] CMatrix = new int[size][size];
		/** base case **/
		if (size == 1)
			CMatrix[0][0] = A[0][0] * B[0][0];
		else {

			// 7 multiplication operations
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

			//combining M matrices using addition and subtraction 
			int[][] C00 = sum(sub((sum(M0, M3, 0, 0, 0, 0, size / 2)), M4, 0, 0, 0, 0, size / 2), M6,
					0, 0, 0, 0,	size / 2);

			int[][] C01 = sum(M2, M4, 0, 0, 0, 0, size / 2);

			int[][] C10 = sum(M1, M3, 0, 0, 0, 0, size / 2);

			int[][] C11 = sum(sub(sum(M0, M2, 0, 0, 0, 0, size / 2), M1, 0, 0, 0, 0, size / 2), M5, 0, 
					0, 0, 0, size / 2);

			//these can be combined to obtain C0,0, C0,1, C1,0, C1,1. 
			//This requires two nested loops (to add columns and rows of matrices).

			joinMatrix(C00, CMatrix, 0, 0);
			joinMatrix(C01, CMatrix, 0, size / 2);
			joinMatrix(C10, CMatrix, size / 2, 0);
			joinMatrix(C11, CMatrix, size / 2, size / 2);	

		}
		return CMatrix;
	}

	/** Function to join the matrix**/
	public void joinMatrix(int[][] C, int[][] CMatrix, int x, int y) {
		
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) 
				CMatrix[x+i][y+j] = C[i][j];
		}
	}
	
	public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
		int[][] array = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				array[i][j] = A[x1 + i][y1 + j] + B[x2 + i][y2 + j];
		}
		return array;
	}

	
	public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
		int[][] array = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				array[i][j] = A[x1 + i][y1 + j] - B[x2 + i][y2 + j];
		}
		return array;

	}

	
	public int[][] initMatrix(int n) {
		// 2d array of n size will be initialized
		int[][] array = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				array[i][j] = 0;
		}
		return array;
	}

	
	public void printMatrix(int n, int[][] A) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	// change it
	public int[][] readMatrix(String filename, int n) throws Exception {
		Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));

		int[][] A = new int[n][n];
		while (sc.hasNextLine()) {
			for (int i = 0; i < n; i++) {
				String[] line = sc.nextLine().trim().split(" "); //idk if we can use this or not 
				for (int j = 0; j < line.length; j++) {
					A[i][j] = Integer.parseInt(line[j]);
				}
			}
		}
		return A;
	}

}