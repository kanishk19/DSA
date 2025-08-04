package interview.question.company.google.find_duplicate_in_ancestors;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Node {
    private final int id;
    private final String name;
    private List<Node> children;

    public String toString(){
        return "Node:{" +
                " id : " + id +
                " name : " + name +
//                " children : " + children +
                " }";
    }
}
