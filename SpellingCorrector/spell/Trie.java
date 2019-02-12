package spell;

import java.lang.StringBuilder;
import java.util.TreeSet;
import java.util.Iterator;

public class Trie implements ITrie {
    private int wordCount = 0;
    private int nodeCount = 1;
    private Node head = new Node();
    private int hashValue = 0;

    public Trie() {}

    public Node getNodeHead() {
        return head;
    }

      /**
  	 * Adds the specified word to the trie (if necessary) and increments the word's frequency count
  	 *
  	 * @param word The word being added to the trie
  	 */
  	public void add(String word) {
        if (word.length() < 1) return;
        hashValue += word.hashCode();
        wordCount++;

        Node currentNode = head;
        Node newNode = null;
        char[] wordArray = word.toCharArray();
        for (int i = 0; i < wordArray.length; i++) {
            if (currentNode.nodes[wordArray[i]-'a'] == null) {
                newNode = new Node();
                currentNode.nodes[wordArray[i]-'a'] = newNode;
                nodeCount++;
            }
            currentNode = currentNode.nodes[wordArray[i]-'a'];
            if (i == wordArray.length - 1) {
                currentNode.addCount();
            }
        }
    }

  	/**
  	 * Searches the trie for the specified word
  	 *
  	 * @param word The word being searched for
  	 *
  	 * @return A reference to the trie node that represents the word,
  	 * 			or null if the word is not in the trie
  	 */
  	public INode find(String word) {
          Node foundNode = null;
          Node currentNode = head;
          char[] wordArray = word.toCharArray();

          for (int i = 0; i < wordArray.length; i++) {
              if (currentNode.nodes[wordArray[i]-'a'] == null) {
                  return null;
              }
              currentNode = currentNode.nodes[wordArray[i]-'a'];
          }
          if(currentNode.getValue() < 1) return null;
          foundNode = currentNode;
          return foundNode;
      }

  	/**
  	 * Returns the number of unique words in the trie
  	 *
  	 * @return The number of unique words in the trie
  	 */
  	public int getWordCount() {
        return wordCount;
    }

  	/**
  	 * Returns the number of nodes in the trie
  	 *
  	 * @return The number of nodes in the trie
  	 */
  	public int getNodeCount() {
        return nodeCount;
    }

    private void toTreeSet(Node node, TreeSet<String> ts, StringBuilder sb) {
        Node[] nodes = node.getNodes();
        for (int i = 0; i < 26; i++) {
            if (nodes[i] == null) continue;
            StringBuilder strb = new StringBuilder(sb.toString());
            char chr = (char) ('a' + i);
            strb.append(chr);
            if (nodes[i].getValue() > 0) {
                ts.add(strb.toString());
            }
            toTreeSet(nodes[i], ts, strb);
        }
    }

	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> ts = new TreeSet<String>();
        Node[] nodes = head.getNodes();
        for (int i = 0; i < 26; i++) {
            if (nodes[i] == null) continue;
            StringBuilder strb = new StringBuilder();
            char chr = (char) ('a' + i);
            strb.append(chr);
            if (nodes[i].getValue() > 0) {
                ts.add(strb.toString());
            }
            toTreeSet(nodes[i], ts, strb);
        }
        Iterator iterator = ts.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            sb.append('\n');
        }
        return sb.toString();
    }

  	@Override
  	public int hashCode() {
          return hashValue * 7;
    }

    private boolean checkNodes(Node node, Node newNode) {
        if(node == null && newNode == null) return true;
        if(node == null && newNode != null) return false;
        if(node != null && newNode == null) return false;
        if(node.getValue() != newNode.getValue()) return false;

        Node[] nodes = node.getNodes();
        Node[] newNodes = newNode.getNodes();
        boolean equals = true;
        for (int i = 0; i < 26; i++) {
            equals = checkNodes(nodes[i], newNodes[i]);
        }
        return equals;
    }

  	@Override
  	public boolean equals(Object o) {
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Trie newTrie = (Trie) o;
        if (newTrie.getNodeCount() != nodeCount) return false;
        if (newTrie.getWordCount() != wordCount) return false;
        Node newHead = newTrie.getNodeHead();

        boolean equal = checkNodes(head, newHead);

        return true;
    }

    public class Node implements ITrie.INode {
        private int count = 0;
        public Node[] nodes = new Node[26];

        /**
         * Returns the frequency count for the word represented by the node
         *
         * @return The frequency count for the word represented by the node
         */
        public int getValue() {
            return count;
        }

        public void addCount() {
            count++;
        }

        public Node[] getNodes() {
            return nodes;
        }
    }

}
