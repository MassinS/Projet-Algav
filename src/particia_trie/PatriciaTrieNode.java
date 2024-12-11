package particia_trie;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatriciaTrieNode {
	//pour faciliter la conversion de fichier json vers arbre
	@JsonProperty("label")
	String label;
	
	@JsonProperty("isEndOfWord")
	boolean isEndOfWord;
	
	@JsonProperty("children")
	Map<Character, PatriciaTrieNode> children;
	
	
	public PatriciaTrieNode() {
        this.label = "";
        this.isEndOfWord = false;
        this.children = new HashMap<>();
    }
	
	
	public PatriciaTrieNode(String label) {
        this.label = label;
        this.isEndOfWord = false;
        this.children = new HashMap<>();
    }	

	
}
