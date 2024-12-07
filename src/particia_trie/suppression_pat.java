package particia_trie;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class suppression_pat {

	public static void main(String[] args) {
		List<String> mots;
		PatriciaTrieNode root = new PatriciaTrieNode("");
		
		 String nomFichier =args[0];//pour recuperer le nom de fichier en question 
		 PatriciaTrie trie = new PatriciaTrie();
		 
			try {
				//transformer le fichier json en arbre
				//lire le fichier qui contient les mots a supprimer
				mots = PatriciaTrie.lireEtDecomposerFichier(nomFichier);
				PatriciaTrieNode nn=trie.Suppression_successif(root,mots);
		     // Serialiser l'arbre en JSON
		        ObjectMapper mappere = new ObjectMapper();
		        String json = mappere.writeValueAsString(nn);
		        PatriciaTrie.ecrireSurfichier("pat.json", json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

	}
	

}
