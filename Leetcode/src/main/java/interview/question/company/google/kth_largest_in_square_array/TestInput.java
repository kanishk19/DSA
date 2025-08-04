package interview.question.company.google.kth_largest_in_square_array;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestInput {
    private int k;
    private List<Integer> numList;
}
