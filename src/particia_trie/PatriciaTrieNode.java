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
	
	@JsonProperty("compteurComparaisons")
	private static int compteurComparaisons = 0; 
	
	
	
	public static void resetCompteur() {
		compteurComparaisons = 0; // Réinitialiser le compteur
	}
	
	public static int getCompteur() {
		return compteurComparaisons; // Récupérer le nombre de comparaisons
	}
	
	public static void incrementCompteur() {
		compteurComparaisons++; // Incrémenter le compteur
	}
	
	
	
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
