package interview.question.company.google.chain_division;

import java.util.Arrays;
import java.util.List;

public class Application {
    private static final ChainDivision chainDivision = new ChainDivision();

    public static void main(String[] args){
        List<Integer> inputList = Arrays.asList(5,2,1,3,2,7);
        System.out.println(chainDivision.isDivisionPossible(inputList));
        inputList = Arrays.asList(5,1,3,2,7);
        System.out.println(chainDivision.isDivisionPossible(inputList));
        validateGeminiTests();
    }

    private static void validateGeminiTests(){
        System.out.println("validating gemini tests");
        // Example 1: [5, 2, 1, 3, 2, 7] -> true
        // Remove 2 (at index 1): Remaining [5, 1, 3, 2, 7]. Total 18. Target 9.
        // Split after 3: [5, 1, 3] (sum 9) and [2, 7] (sum 9).
        System.out.println("--- Example 1 ---");
        List<Integer> weights1 = Arrays.asList(5, 2, 1, 3, 2, 7);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights1)); // Expected: true

        // Example 2: [1, 1, 1, 1] -> false (total remaining is 3, odd)
        System.out.println("\n--- Example 2 ---");
        List<Integer> weights2 = Arrays.asList(1, 1, 1, 1);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights2)); // Expected: false

        // Example 3: [10, 5, 5] -> true
        // Remove 10 (at index 0): Remaining [5, 5]. Total 10. Target 5.
        // Split after first 5: [5] and [5].
        System.out.println("\n--- Example 3 ---");
        List<Integer> weights3 = Arrays.asList(10, 5, 5);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights3)); // Expected: true

        // Example 4: [7, 3, 4] -> false
        System.out.println("\n--- Example 4 ---");
        List<Integer> weights4 = Arrays.asList(7, 3, 4);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights4)); // Expected: false

        // Custom Test Case 5: Large values
        System.out.println("\n--- Custom Test Case 5: Large values ---");
        List<Integer> weights5 = Arrays.asList(10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000); // 10 links, sum 100,000
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights5)); // Expected: false

        // Custom Test Case 6: Chain of 2 links
        System.out.println("\n--- Custom Test Case 6: 2 links ---");
        List<Integer> weights6 = Arrays.asList(10, 10);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights6)); // Expected: false

        // Custom Test Case 7: Chain of 3 links, remove middle
        System.out.println("\n--- Custom Test Case 7: 3 links, remove middle ---");
        List<Integer> weights7 = Arrays.asList(1, 5, 1);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights7)); // Expected: true

        // Custom Test Case 8: Chain of 3 links, remove first
        System.out.println("\n--- Custom Test Case 8: 3 links, remove first ---");
        List<Integer> weights8 = Arrays.asList(1, 1, 2);
        System.out.println("Result: " + chainDivision.isDivisionPossible(weights8)); // Expected: true
    }
}
