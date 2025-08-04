package interview.question.company.google.find_duplicate_in_ancestors;

import commons.Pair;

import java.util.*;

public class DictionaryService {
    /**
     * Clarifying Questions:
     *  - how do we return the output:
     *       - currently returning it as a pair of duplicates
     *       - what if there are multiple duplicate ancestors
     * */
    public void printDictionary(Node root){
        if(root == null)
            return;

        Deque<Node> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()){
            int currLevelSize = queue.size();
            while (currLevelSize > 0){
                Node currNode = queue.poll();
                System.out.print(currNode.getId() + " ");
                List<Node> children = currNode.getChildren();
                for(Node child: children){
                    queue.add(child);
                }
                currLevelSize -= 1;
            }
            System.out.println();
        }
    }

    public Set<Pair<Node, Node>> findDuplicateAncestor(Node root, Optional<Integer> maxLevels){
        if(root == null)
            return Collections.EMPTY_SET;

        Set<Pair<Node, Node>> duplicateSet = new HashSet<>();
        Map<String, Pair<Integer, Node>> currPath = new HashMap<>();
        Integer effectiveMaxLevels = maxLevels.orElse(Integer.MAX_VALUE);
        traverseDFS(root, duplicateSet, currPath, 0, effectiveMaxLevels);
        return duplicateSet;
    }

    private void traverseDFS(Node node, Set<Pair<Node, Node>> duplicateSet, Map<String, Pair<Integer, Node>> currPath, int currLevel, int maxLevels){
        if(node == null)
            return;

        if(currPath.containsKey(node.getName())){
            Node duplicateNode = currPath.get(node.getName()).getValue();
            int duplicateLevel = currPath.get(node.getName()).getKey();
            if(currLevel - duplicateLevel <= maxLevels) {
                duplicateSet.add(new Pair<>(duplicateNode, node));
            }
        }

        currPath.put(node.getName(), new Pair<>(currLevel, node));
        List<Node> children = node.getChildren();
        for(Node child: children){
            traverseDFS(child, duplicateSet, currPath, currLevel + 1, maxLevels);
        }
        currPath.remove(node.getName());
    }


}
