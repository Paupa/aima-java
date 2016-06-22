package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.List;

import aima.core.util.datastructure.XYLocation;

/**
 * @author Paula Díaz Puertas
 */
public class HungarianAlgorithmHeuristicFunction extends AbstractTSPHeuristicFunction {
	
	private int matrixSize = 0;
	private Integer[][] matrix = null;

	@Override
	public double h(Object state) {

		// We get the city where we are starting from + the ones we want to
		// visit
		List<City> toVisit = this.getCitiesForRoutes(state);

		// We save the size of the matrix we will use for the algorithm
		matrixSize = toVisit.size() - 1;

		// Matrix where we save the costs for going from cities(0, matrixSize -
		// 1) to cities(1, matrixSize)
		// It'll be modified by the algorithm
		matrix = new Integer[matrixSize][matrixSize];

		// The original algorithm doesn't count with null values.
		// Having a null value means that the assignment cannot be done
		// If a matrix has a row or a column full of nulls it means that the
		// problem cannot be solved

		// We cannot check if each row and column has a non-null value with just
		// a double for-loop,
		// so I use this array to check the columns while I check the rows in
		// the loop.
		boolean[] columnHasNonNullCost = new boolean[matrixSize];

		// We fill the matrix with the costs
		for (int i = 0; i < matrixSize; i++) {

			// Here we check if the row has any non-null value
			boolean rowHasNonNullCost = false;

			for (int j = 1; j < matrixSize + 1; j++) {
				Integer cost = toVisit.get(i).getCost(toVisit.get(j));
				matrix[i][j - 1] = cost;

				if (cost != null) {
					rowHasNonNullCost = true;
					columnHasNonNullCost[j - 1] = true;
				}
			}

			// If the row has all null values we return the max value, because
			// the problem cannot be solved
			if (!rowHasNonNullCost)
				return Double.MAX_VALUE;
		}

		// We check the array to see if any column has all null values
		for (int i = 0; i < matrixSize; i++) {
			// If the column has all null values we return the max value,
			// because the problem cannot be solved
			if (!columnHasNonNullCost[i])
				return Double.MAX_VALUE;
		}

		// Here starts the real algorithm

		// We create zeros on each row
		for (int i = 0; i < matrixSize; i++) {
			Integer minCost = null;

			for (int j = 0; j < matrixSize; j++) {
				Integer rowCost = matrix[i][j];

				if (rowCost != null && (minCost == null || rowCost < minCost))
					minCost = rowCost;
			}

			if (minCost != null && minCost != 0) {
				for (int j = 0; j < matrixSize; j++) {
					Integer cost = matrix[i][j];
					if (cost != null)
						matrix[i][j] = cost - minCost;
				}
			}
		}

		// We create zeros on each column
		for (int j = 0; j < matrixSize; j++) {
			Integer minCost = null;

			for (int i = 0; i < matrixSize; i++) {
				Integer columnCost = matrix[i][j];

				if (columnCost != null && (minCost == null || columnCost < minCost))
					minCost = columnCost;
			}

			if (minCost != null && minCost != 0) {
				for (int i = 0; i < matrixSize; i++) {
					Integer cost = matrix[i][j];
					if (cost != null)
						matrix[i][j] = cost - minCost;
				}
			}
		}

		int linesUsedToCover = 0;

		// If the lines equal the size of the matrix, we have found a solution.
		while (linesUsedToCover != matrixSize) {

			// Now we cover rows or columns to try to cover every zero in the
			// matrix with the fewer lines possible
			boolean[] rowCovered = new boolean[matrixSize];
			boolean[] columnCovered = new boolean[matrixSize];

			for (int i = 0; i < matrixSize; i++) {
				for (int j = 0; j < matrixSize; j++) {

					// If the value at (i, j) is zero and hasn't been covered...
					if (matrix[i][j] != null && matrix[i][j] == 0 && !rowCovered[i] && !columnCovered[j]) {
						// We check the rest of the values of the column,
						// starting with the inmediate next
						for (int k = i + 1; k < matrixSize; k++) {

							if (matrix[k][j] != null && matrix[k][j] == 0) {
								columnCovered[j] = true;
								break;
							}
						}

						// If the column hasn't been covered we cover the row to
						// cover this zero
						if (!columnCovered[j])
							rowCovered[i] = true;
					}
				}
			}

			// Now we check how many lines we have used to cover the zeros
			// This way we now if we have solved the problem already or if we
			// need to manipulate more the matrix
			linesUsedToCover = 0;
			
			for (int k = 0; k < matrixSize; k++) {
				if (rowCovered[k])
					linesUsedToCover++;

				if (columnCovered[k])
					linesUsedToCover++;
			}

			// If the lines are less than the size of the matrix it means we
			// haven't found a solution yet, so we need to alter the matrix
			if (linesUsedToCover != matrixSize) {
				int minWithoutCover = Integer.MAX_VALUE;

				// We find the minimum value that hasn't been covered
				for (int i = 0; i < matrixSize; i++) {

					if (!rowCovered[i]) {
						for (int j = 0; j < matrixSize; j++) {

							if (!columnCovered[j]) {
								Integer value = matrix[i][j];
								if (value != null && value < minWithoutCover)
									minWithoutCover = value;
							}
						}
					}
				}

				// We substract the minimum value to all the values that aren't
				// covered
				for (int i = 0; i < matrixSize; i++) {

					if (!rowCovered[i]) {
						for (int j = 0; j < matrixSize; j++) {

							if (!columnCovered[j]) {
								Integer value = matrix[i][j];
								if (value != null)
									matrix[i][j] = value - minWithoutCover;
							}
						}
					}
				}

				// We add the minimum value to all the values that are covered
				// twice
				for (int i = 0; i < matrixSize; i++) {

					if (rowCovered[i]) {
						for (int j = 0; j < matrixSize; j++) {

							if (columnCovered[j]) {
								Integer value = matrix[i][j];
								if (value != null)
									matrix[i][j] = value + minWithoutCover;
							}
						}
					}
				}

			}

		}

		// If we are out of the while loop it means the lines equal the size of 
		// the matrix, and so we have found a solution
		
		List<XYLocation> result = findSolution();
		
		// Now that we have the positions of the best assignments we get 
		// the cost of the solution 
		int totalCost = 0;
		
		for(XYLocation location : result) {
			totalCost += toVisit.get(location.getXCoOrdinate()).getCost(toVisit.get(location.getYCoOrdinate() + 1));
		}
		
		return totalCost;
		
	}
	
	/**
	 * This method tries to find a solution based on the attribute matrix
	 * 
	 * @return It returns a list with the locations of the zeros selected 
	 * 		for the solution. If a solution can't be found, it returns null.
	 */
	private List<XYLocation> findSolution() {
		
		int[] zerosOnRow = new int[matrixSize];
		
		// We fill the array with how many zeros there are in each row
		for(int i = 0; i < matrixSize; i++) {
			int zeros = 0;
			for(int j = 0; j < matrixSize; j++) {
				if(matrix[i][j] != null && matrix[i][j] == 0)
					zeros++;
			}
			
			zerosOnRow[i] = zeros;
		}
		
		boolean[] columnsUsed = new boolean[matrixSize];
		
		return findSolution(zerosOnRow, columnsUsed);
	}
	
	/**
	 * This method finds the solution through a backtracking algorithm.
	 * It chooses the row with fewer zeros (but more than zero) and
	 * tries to find a solution with each zero of the row.
	 * 
	 * @param zerosOnRow This array saves the count of how many selectionable
	 * 		zeros there are in each row
	 * @param columnsUsed This array saves which columns have been used to find a solution
	 * @return It returns a list with the locations of the zeros selected 
	 * 		for the solution. If there can't be found a solution with the arguments
	 * 		passed, it returns null.
	 */
	private List<XYLocation> findSolution(int[] zerosOnRow, boolean[] columnsUsed) {
		
		// First we find which row has less zeros, ignoring the ones without zeros
		int row = -1;
		int minZeros = Integer.MAX_VALUE;
		
		for(int i = 0; i < matrixSize; i++) {
			int zeros = zerosOnRow[i];
			if(zeros != 0 && zeros < minZeros) {
				row = i;
				minZeros = zeros;
			}
		}
		
		// If all the rows haven't any zeros it means that we already found a solution
		// or that there can't be found any solution in the current state
		if(row == -1) {
			
			// If we find there's some column that isn't used it means that 
			// there's no solution with the current state
			for(int j = 0; j < matrixSize; j++)
				if(!columnsUsed[j])
					return null;
			
			//If all the columns has been used it means the current state is a solution
			return new ArrayList<XYLocation>();
		}
		
		// We search the zeros in the row that can be used in the current state
		// to find a solution
		for(int j = 0; j < matrixSize; j++) {
			if(matrix[row][j] != null && matrix[row][j] == 0 && !columnsUsed[j]) {
				// We try to use this zero
				int[] copyZerosOnRow = zerosOnRow.clone();
				boolean[] copyColumnsUsed = columnsUsed.clone();
				
				// We mark the current column has used
				copyColumnsUsed[j] = true;
				
				// We put the count of this row at 0, because it has been used
				copyZerosOnRow[row] = 0;
				
				// We decrement the count of zeros of each row that has a zero 
				// on the column selected
				for(int i = 0; i < matrixSize; i++) {
					if(copyZerosOnRow[i] > 0 && matrix[i][j] != null && matrix[i][j] == 0)
						copyZerosOnRow[i]--;
				}
				
				//We try to find the solution through backtracking
				List<XYLocation> result = findSolution(copyZerosOnRow, copyColumnsUsed);
				
				//If there is a solution with the current state
				if(result != null) {
					//We add the location we chose to the result
					result.add(new XYLocation(row, j));
					return result;
				}
				
			}
		}
		
		//If there can't be found any solution in the current state, we return null
		return null;
	}

}
