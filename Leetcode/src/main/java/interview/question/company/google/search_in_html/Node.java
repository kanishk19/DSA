package interview.question.company.google.search_in_html;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // For potential equals/hashCode if Node instances need semantic comparison

// Represents a node in the HTML content tree.
// A node can be either a text node or an HTML tag node.
class Node {
    private boolean isTextNode;
    private String content; // If isTextNode is true, this is the text content.
    // If isTextNode is false, this is the HTML tag name (e.g., "div", "span").
    private List<Node> children; // Children nodes (only applicable for HTML tag nodes)

    // Constructor for a TEXT Node
    public Node(String text) {
        this.isTextNode = true;
        this.content = text;
        this.children = new ArrayList<>(); // Text nodes logically don't have children in this model
    }

    // Constructor for an HTML TAG Node
    public Node(String tagName, List<Node> children) {
        this.isTextNode = false;
        this.content = tagName;
        this.children = (children != null) ? new ArrayList<>(children) : new ArrayList<>();
    }

    // --- Provided methods from the problem description ---
    public boolean isText() {
        return isTextNode;
    }

    public String getText() {
        if (!isTextNode) {
            // It's good practice to handle calling getText() on a non-text node
            // For simplicity, we return an empty string, but throwing an exception might be better
            return "";
        }
        return content;
    }

    public List<Node> getChildren() {
        return children;
    }

    // --- Helper methods for building and debugging the tree ---

    // Adds a child node to an HTML tag node
    public void addChild(Node child) {
        if (isTextNode) {
            throw new UnsupportedOperationException("Text nodes cannot have children.");
        }
        this.children.add(child);
    }

    // Returns the tag name for an HTML tag node, or "TEXT_NODE" for a text node.
    public String getTagName() {
        if (isTextNode) {
            return "TEXT_NODE";
        }
        return content;
    }

    @Override
    public String toString() {
        if (isTextNode) {
            return "TEXT:\"" + content + "\"";
        } else {
            return "<" + content + ">";
        }
    }

    // IMPORTANT: For HashSet to correctly identify unique Node objects by reference,
    // default equals and hashCode are usually sufficient if each Node instance
    // in your tree is unique. If you might have different Node objects that are
    // logically "equal" (e.g., same content/tag name), you would need to
    // override equals() and hashCode() based on their semantic identity.
    // For this problem's context, where we are returning references to the
    // original tree nodes, the default object identity is usually fine.
}