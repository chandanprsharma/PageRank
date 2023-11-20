package com.lseg.assignment.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lseg.assignment.ranking.model.MemberWithWeight;

/*
 * Attachment: Find the requirement from attached "WeightedRanking.png" file
 * 
 * Assumptions for Member ranking
 * ------------------------------------
 * 1. Measuring consistent iteration up to TWO continuous successful iterations
 * 2. Criteria to consider a rank consistent if difference b/w two consecutive ranking is less then .45
 * 
 * Computation:
 * - Storing rank of member i -> in matrix[i][i]
 */
public class MemberRankingWithWeight {

	public static void main(String[] args) {
		// Initialized matrix with problem statement given in version-1
		// Initial ranking of a member computed as 1/n where, n is total number of
		// member
		float[][] matrix = { 
				{ 1 / 5f, 1, 1, 1, 0 }, 
				{ 1, 1 / 5f, 1, 0, 0 }, 
				{ 1, 0, 1 / 5f, 1, 0 },
				{ 1, 0, 0, 1 / 5f, 0 }, 
				{ 1, 1, 1, 1, 1 / 5f } 
			};
		int row = matrix.length;

		matrix = computeWeight(matrix, row);

		List<MemberWithWeight> memberList = new ArrayList<MemberWithWeight>();
		for (int i = 0; i < row; i++) {
			memberList.add(new MemberWithWeight(i + 1, matrix[i][i]));
		}

		List<Integer> rankList = memberList.stream().sorted((m1, m2) -> m2.compareTo(m1)).collect(Collectors.toList())
				.stream().map(m -> m.getId()).collect(Collectors.toList());
		System.out.println(rankList);
	}

	public static float[][] computeWeight(float[][] matrix, int row) {
		// To capture successful consistentIteration of each member
		boolean stageLimit[] = new boolean[row];
		// To hold computational weight of current member
		float weightSum = 0;
		int consistentIteration = 0;
		while (consistentIteration < 2) {
			for (int i = 0; i < row; i++) {
				weightSum = 0;
				for (int j = 0; j < matrix[i].length; j++) {
					if (i != j && matrix[i][j] != 0) {
						// Computing sum of weight of all reporting member (matrix[j][i] != 0)
						// Divided by total number out going link for each reporting member (j)
						weightSum += (matrix[j][j] / outColSum(matrix, j));
					}
				}
				if (Math.abs(matrix[i][i] - weightSum) <= 0.45)
					stageLimit[i] = true;
				matrix[i][i] = weightSum;
			}
			if (isConsistentIteration(stageLimit)) {
				consistentIteration += 1;
				// Reset stage limit after a successful consistent iteration
				for (int i = 0; i < row; i++)
					stageLimit[i] = false;
			} else {
				// Reset the consistent iteration counter in case if a in b/w iteration
				// doesn't qualify for consistent
				consistentIteration = 0;
			}
		}

		return matrix;
	}

	/*
	 * To check if given iteration is consistent or not - If all the member rank are
	 * within < 0.45 which implies their corresponding stageLimit is true
	 */

	private static boolean isConsistentIteration(boolean[] stageLimit) {
		boolean flag = true;
		for (int i = 0; i < stageLimit.length; i++) {
			if (!stageLimit[i]) {
				flag = false;
				break;
			}

		}
		return flag;
	}

	// To calculate all out going link for given reporting member 'j'
	private static int outColSum(float[][] matrix, int j) {
		int colSum = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][j] != 0 && i != j)
				colSum += 1;
		}
		return colSum;
	}
}
