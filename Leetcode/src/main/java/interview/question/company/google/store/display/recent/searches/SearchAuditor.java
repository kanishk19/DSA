package interview.question.company.google.store.display.recent.searches;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SearchAuditor {
    Map<String, Node> queryToNodeMap;
    Node left;
    Node right;
    int searchLimit;

    public  SearchAuditor(int searchLimit){
        this.searchLimit = searchLimit;
        queryToNodeMap = new HashMap<>();
        left = new Node();
        right = new Node();

        left.next = right;
        right.prev = left;
    }

    public void addSearch(String searchQuery){
        Node queryNode;
        if(queryToNodeMap.containsKey(searchQuery)){
            queryNode = queryToNodeMap.get(searchQuery);
            removeNode(queryNode);
        }
        else {
            queryNode = new Node(searchQuery);
            queryToNodeMap.put(searchQuery, queryNode);
        }

        insertAtTop(queryNode);
    }

    public List<String> getRecentSearches(){
        List<String> mostRecentSearchesList = new ArrayList<>();
        int counter = 0;
        Node runningNode = left.next;
        while(counter < searchLimit && runningNode != right){
            mostRecentSearchesList.add(runningNode.searchQuery);
            counter += 1;
            runningNode = runningNode.next;
        }

        return mostRecentSearchesList;
    }

    private void insertAtTop(Node node){
        Node nextNode = left.next;
        node.next = nextNode;
        node.prev = left;

        left.next = node;
        nextNode.prev = node;
    }

    private void removeNode(Node node){
        Node prevNode = node.prev;
        Node nextNode = node.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        node.next = null;
        node.prev = null;
    }

    class Node{
        String searchQuery;
        Node next;
        Node prev;

        public Node(String searchQuery){
            this.searchQuery = searchQuery;
            this.next = null;
            this.prev = null;
        }

        public Node(){
            this.next = null;
            this.prev = null;
        }
    }

}


