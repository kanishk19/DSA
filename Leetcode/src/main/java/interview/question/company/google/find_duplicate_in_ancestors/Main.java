package interview.question.company.google.find_duplicate_in_ancestors;

import commons.Pair;

import java.util.*;

public class Main {
    private final static DictionaryService dictionaryService = new DictionaryService();

    public static void main(String[] args){
        Node root = buildDummyInput();
        dictionaryService.printDictionary(root);
        Set<Pair<Node, Node>> duplicateSet = dictionaryService.findDuplicateAncestor(root, Optional.of(2));

        for(Pair<Node, Node> duplicatePair: duplicateSet){
            Node dupNode1 = duplicatePair.getKey();
            Node dupNode2 = duplicatePair.getValue();

            System.out.println(dupNode1 + " " + dupNode2);
        }
    }

    private static Node buildDummyInput(){
        Node node1 = new Node(1, "1", Collections.EMPTY_LIST);
        Node node2 = new Node(2, "2", Collections.EMPTY_LIST);
        Node node3 = new Node(3, "3", Collections.EMPTY_LIST);
        Node node4 = new Node(4, "4", Collections.EMPTY_LIST);
        Node node5 = new Node(5, "5", Collections.EMPTY_LIST);
        Node node6 = new Node(6, "6", Collections.EMPTY_LIST);
        Node node7 = new Node(7, "1", Collections.EMPTY_LIST);

        List<Node> node3children = Arrays.asList(node7);
        List<Node> node2children = Arrays.asList(node5, node6);
        List<Node> node1children = Arrays.asList(node2, node3, node4);

        node1.setChildren(node1children);
        node3.setChildren(node3children);
        node2.setChildren(node2children);

        return node1;
    }
}
