package particia_trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	public static void main (String [] args) {
		
		 // Structure 1 : Patricia-Tries
		
		// Question 1.1 : La caractère choisi est : ~
		
		// Question 1.2 : les primitives de base sont codés dans le fichier PatriciaTrie.java
		
		// Question 1.3 : La construction de l'arbre d'exemple de base par ajouts successifs : 
		
		
		// la phrase à encoder : 
		
		String exemple_de_base ="A~quel~genial~professeur~de~dactylographie~sommes~nous~redevables~de~la~superbe~phrase"
				+ "~ci~dessous~,~un~modele~du~genre~,~que~toute~dactylo~connait~par~coeur~puisque~elle~fait~"
				+ "appel~a~chacune~des~touches~du~clavier~de~la~machine~a~ecrire~?";
		
        // Récuperation des mots séparés //
		
		String[] words = exemple_de_base.split("~");
		
	
		List<String> maListe = new ArrayList<>(Arrays.asList(words));
		
		
	
		
		PatriciaTrieNode root1 = new PatriciaTrieNode("");

		PatriciaTrie trie = new PatriciaTrie();
		
		trie.ajout_successif(root1, maListe);
	    
		root1=trie.Suppression_successif(root1, maListe);
	    
		trie.AfficherLesMots(root1, "");
		System.out.println(trie.ComptageMots(root1));
	     
	    
		        
			 
		
	}
	
	
}
