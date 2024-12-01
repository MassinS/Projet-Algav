package particia_trie;

import java.util.HashMap;
import java.util.Map;

public class PatriciaTrieNode {

	String label;
	boolean isEndOfWord;
	Map<Character, PatriciaTrieNode> children;
	
	PatriciaTrieNode(String label) {
        this.label = label;
        this.isEndOfWord = false;
        this.children = new HashMap<>();
    }	

}
