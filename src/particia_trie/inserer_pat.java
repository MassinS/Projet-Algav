package particia_trie;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class inserer_pat {

	public static void main(String[] args) {
		List<String> mots;
		PatriciaTrieNode root = new PatriciaTrieNode("");
		
		 String nomFichier =args[0];//pour recuperer le nom de fichier en question
		 File dossier = new File("."); // "." represente le repertoire courant
		 File[] fichiers = dossier.listFiles((dir, name) -> name.matches(".*pat.*\\.json"));//recuperer tout les fichier qui ont un nom trie
		 PatriciaTrie trie = new PatriciaTrie();
		 //cree un objet de type Tries_Hybrides pour utiliser la fonction ecrireSurfichier qui permet de creer un fichier un contenu donne.
		 
			try {
				mots = PatriciaTrie.lireEtDecomposerFichier(nomFichier);
		        PatriciaTrieNode nn= trie.ajout_successif(root, mots);
		        // Serialiser l'arbre en JSON
		        ObjectMapper mapper = new ObjectMapper();
		        String json = mapper.writeValueAsString(nn);
		        if(fichiers.length==0) {
		        	PatriciaTrie.ecrireSurfichier("pat.json", json);
		        }else {
		        	PatriciaTrie.ecrireSurfichier("pat"+fichiers.length+".json", json);
		        }
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
	}

}
