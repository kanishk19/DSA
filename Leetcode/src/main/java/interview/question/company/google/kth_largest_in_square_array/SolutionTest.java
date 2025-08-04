package interview.question.company.google.kth_largest_in_square_array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void getKthSquareNumberHappyCase() {
        TestInput testInput = buildHappyInput();
        int kthLargest = solution.getKthSquareNumber(testInput);
        assertEquals(2, kthLargest);
    }

    private TestInput buildHappyInput(){
        return TestInput.builder()
                .k(3)
                .numList(Arrays.asList(1,2,3)).build();
    }
}