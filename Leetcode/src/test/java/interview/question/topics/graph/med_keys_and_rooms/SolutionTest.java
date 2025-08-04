package interview.question.topics.graph.med_keys_and_rooms;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SolutionTest {
    private Solution solution;
    private List<List<Integer>> defaultRooms;

    @BeforeEach
    void setUp() {
        solution = new Solution();
        defaultRooms = Arrays.asList(
                new ArrayList<>(Arrays.asList(1)),
                new ArrayList<>(Arrays.asList(2)),
                new ArrayList<>(Arrays.asList(3)),
                Collections.EMPTY_LIST
        );
    }

    @Test
    void canVisitAllRoomsHappyCase() {
        boolean response = solution.canVisitAllRooms(defaultRooms);
        Assert.assertEquals(true, response);
    }

    @Test
    void canVisitSkewedGraph(){
        List<List<Integer>> rooms = generateSkewedGraph();
        boolean response = solution.canVisitAllRooms(rooms);
        Assert.assertEquals(true, response);
    }

    private List<List<Integer>> generateSkewedGraph(){
        List<List<Integer>> rooms = Arrays.asList(
                new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(2)), new ArrayList<>(Arrays.asList(3)), new ArrayList<>(Arrays.asList(4)),
                new ArrayList<>(Arrays.asList(5)), new ArrayList<>(Arrays.asList(6)), new ArrayList<>(Arrays.asList(7)), new ArrayList<>(Arrays.asList(8)),
                new ArrayList<>(Arrays.asList(9)), new ArrayList<>(Arrays.asList(10)), new ArrayList<>(Arrays.asList(11)), new ArrayList<>(Arrays.asList(12)),
                new ArrayList<>(Arrays.asList(13)), new ArrayList<>(Arrays.asList(14)), new ArrayList<>(Arrays.asList(15)), new ArrayList<>(Arrays.asList(16)),
                new ArrayList<>(Arrays.asList())
        );
        return rooms;
    }
}