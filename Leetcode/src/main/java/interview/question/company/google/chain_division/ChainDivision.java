package interview.question.company.google.chain_division;

import commons.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainDivision {
    public boolean isDivisionPossible(List<Integer> weights){
        boolean inputValidationResponse = validateInput(weights);
        if(!inputValidationResponse){
            return false;
        }
        Pair<Set<Integer>, Integer> prefixSumPair = computePrefixSumSet(weights);
        Set<Integer> prefixSumSet = prefixSumPair.getKey();
        Integer totalSum = prefixSumPair.getValue();

        for(int weight: weights){
            int remainingSum = totalSum - weight;
            if(remainingSum % 2 != 0){
//                System.out.println("cannot give weight: " + weight + " as charity, as remaining sum is not divisible by 2");
                continue;
            }

            int targetSum = remainingSum/2;
            if(prefixSumSet.contains(targetSum) || prefixSumSet.contains(targetSum + weight)){
                return true;
            }
//            System.out.println("cannot give weight: " + weight + " as charity, as remaining sum cannot be evenly divided in 2 halves");
        }
        return false;
    }

    private Pair<Set<Integer>, Integer> computePrefixSumSet(List<Integer> weights){
        Set<Integer> prefixSumSet = new HashSet<>();
        int runningSum = 0;
        for(int weight: weights){
            runningSum += weight;
            prefixSumSet.add(runningSum);
        }
        return new Pair<>(prefixSumSet, runningSum);
    }

    private boolean validateInput(List<Integer> weights){
        if(weights == null || weights.size() == 0){
            return false;
        }
        return true;
    }
}
