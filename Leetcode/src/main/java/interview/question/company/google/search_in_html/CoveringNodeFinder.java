package interview.question.company.google.search_in_html;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections; // For unmodifiable list or sorting if needed

public class CoveringNodeFinder {

    // Helper class to store character and its originating Node*
    // Using a record (Java 16+) or a private static class.
    // For broader compatibility, a static inner class is used here.
    private static class CharNodeMapping {
        char character;
        Node sourceNode; // This will always be a TEXT Node

        CharNodeMapping(char character, Node sourceNode) {
            this.character = character;
            this.sourceNode = sourceNode;
        }
    }

    /**
     * Finds the nodes that cover the search string within the HTML content tree.
     * This method returns a list of individual TEXT nodes that either directly contain
     * the search string or collectively form it across multiple text nodes.
     *
     * @param head The head (root) node of the HTML content tree.
     * @param searchString The string to search for.
     * @return A list of unique Node objects (TEXT nodes) that cover the search string.
     */
    public List<Node> findCoveringNodes(Node head, String searchString) {
        // Use a Set to collect unique Node objects that cover the string,
        // preventing duplicates if a Node is part of multiple matches.
        Set<Node> uniqueCoveringNodes = new HashSet<>();

        // Handle edge cases: null head or empty search string
        if (head == null || searchString == null || searchString.isEmpty()) {
            return new ArrayList<>(uniqueCoveringNodes);
        }

        // Phase 1: Flatten the HTML tree into a linear sequence of characters with Node* mappings
        // This list will store pairs of (character, source_text_node_pointer)
        List<CharNodeMapping> flattenedTextMap = new ArrayList<>();

        // Recursive DFS helper to populate flattenedTextMap
        flattenDFS(head, flattenedTextMap);

        // Concatenate all characters from the map into a single string for efficient searching
        StringBuilder fullDocumentTextBuilder = new StringBuilder();
        for (CharNodeMapping mapping : flattenedTextMap) {
            fullDocumentTextBuilder.append(mapping.character);
        }
        String fullDocumentText = fullDocumentTextBuilder.toString();

        // Phase 2: Search the flattened string and map results back to original nodes

        // Find all occurrences of the search string in the full document text
        int pos = 0;
        while ((pos = fullDocumentText.indexOf(searchString, pos)) != -1) {
            int matchStartIndex = pos;
            int matchEndIndex = pos + searchString.length() - 1;

            // Iterate through the range of characters that form the current match
            // and add their corresponding source TEXT nodes to our set
            for (int i = matchStartIndex; i <= matchEndIndex; ++i) {
                // The 'sourceNode' in CharNodeMapping will always be a TEXT Node
                uniqueCoveringNodes.add(flattenedTextMap.get(i).sourceNode);
            }

            // Move the search position to the character after the current match
            // to find the next occurrence
            pos++;
        }

        // Convert the set of unique nodes to a List to return
        return new ArrayList<>(uniqueCoveringNodes);
    }

    /**
     * Helper method for Phase 1: Performs a Depth-First Search (DFS)
     * to flatten the HTML tree and populate the character-to-node map.
     * This method is called recursively.
     */
    private void flattenDFS(Node node, List<CharNodeMapping> flattenedTextMap) {
        if (node == null) {
            return;
        }

        if (node.isText()) {
            // If it's a text node, add each of its characters
            // along with a reference to this node to the map.
            String text = node.getText();
            for (char c : text.toCharArray()) {
                flattenedTextMap.add(new CharNodeMapping(c, node));
            }
        } else { // It's an HTML tag node
            // Recursively visit all children
            for (Node child : node.getChildren()) {
                flattenDFS(child, flattenedTextMap);
            }
        }
    }
}
