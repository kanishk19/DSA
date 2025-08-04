package interview.question.company.uber.min_time_for_all_cars_at_common_loc;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private Solution minCarTimeSolution;
    @BeforeEach
    void setUp() {
        minCarTimeSolution = new Solution();
    }

    @Test
    void computeMinTime() {
        List<Integer> locationList = Arrays.asList(1, 3, 7);
        List<Integer> speedList = Arrays.asList(2, 1, 1);

        int minTimeTaken = minCarTimeSolution.computeMinTime(locationList, speedList, 3);
        Assert.assertEquals(2, minTimeTaken);
    }
}