package interview.question.company.google.search_in_html;

import java.util.Arrays;
import java.util.List;

public class Main {
    // Helper function to print the identified nodes in a readable format
    public static void printNodes(List<Node> nodes, String description) {
        System.out.print(description + ": [");
        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);
            System.out.print(node.toString()); // Uses Node's overridden toString
            if (i < nodes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        CoveringNodeFinder finder = new CoveringNodeFinder();

        // Example 1: Single Node Match
        // <div>TEXT:"hello world"</div>, search "world"
        Node text1 = new Node("hello world");
        Node div1 = new Node("div", Arrays.asList(text1));
        List<Node> result1 = finder.findCoveringNodes(div1, "world");
        printNodes(result1, "Result 1 (single node match)"); // Expected: [TEXT:"hello world"]

        System.out.println("\n---");

        // Example 2: Combination of Nodes Match
        // <div><span>TEXT:"Hello"</span><b>TEXT:" "</b><i>TEXT:"World!"</i></div>, search "Hello World"
        Node text2_hello = new Node("Hello"); // N1
        Node span2 = new Node("span", Arrays.asList(text2_hello));

        Node text2_space = new Node(" "); // N2
        Node b2 = new Node("b", Arrays.asList(text2_space));

        Node text2_world = new Node("World!"); // N3
        Node i2 = new Node("i", Arrays.asList(text2_world));

        Node div2 = new Node("div", Arrays.asList(span2, b2, i2));
        List<Node> result2 = finder.findCoveringNodes(div2, "Hello World");
        // Expected: [TEXT:"Hello", TEXT:" ", TEXT:"World!"] (order might vary due to Set, but content is key)
        printNodes(result2, "Result 2 (combination match)");

        System.out.println("\n---");

        // Example 3: Multiple Matches in different parts of a single Text Node
        // <div>TEXT:"apple, banana, apple pie"</div>, search "apple"
        Node text3_full = new Node("apple, banana, apple pie");
        Node div3 = new Node("div", Arrays.asList(text3_full));
        List<Node> result3 = finder.findCoveringNodes(div3, "apple");
        // Expected: [TEXT:"apple, banana, apple pie"] (since both "apple" instances are in the same TEXT node)
        printNodes(result3, "Result 3 (multiple in one text node)");

        System.out.println("\n---");

        // Example 4: No Match
        Node text4 = new Node("orange");
        Node div4 = new Node("div", Arrays.asList(text4));
        List<Node> result4 = finder.findCoveringNodes(div4, "grape");
        printNodes(result4, "Result 4 (no match)"); // Expected: []

        System.out.println("\n---");

        // Example 5: Complex Nesting and Spanning Match
        // <div>TEXT:"Part one "<b>TEXT:"of the "</b><span>TEXT:"big test."</span></div>, search "one of the big"
        Node text5_part_one = new Node("Part one "); // N5_1
        Node text5_of_the = new Node("of the "); // N5_2
        Node b5 = new Node("b", Arrays.asList(text5_of_the));
        Node text5_big_test = new Node("big test."); // N5_3
        Node span5 = new Node("span", Arrays.asList(text5_big_test));
        Node div5 = new Node("div", Arrays.asList(text5_part_one, b5, span5));
        List<Node> result5 = finder.findCoveringNodes(div5, "one of the big");
        // Expected: [TEXT:"Part one ", TEXT:"of the ", TEXT:"big test."]
        printNodes(result5, "Result 5 (complex spanning)");
    }
}
