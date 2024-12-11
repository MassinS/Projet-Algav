package etude;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import particia_trie.PatriciaTrie;
import particia_trie.PatriciaTrieNode;

public class Patricia_trie_Encode_Shakespeare {

	
	public static void main(String[] args) {
	
		String cheminRepertoire = "Shakespeare";
		PatriciaTrie trie = new PatriciaTrie();
        File dossier = new File(cheminRepertoire);
        List<String> mots1;
        PatriciaTrieNode root = new PatriciaTrieNode("");
		
        // Vérifier si c'est un répertoire valide
        if (dossier.isDirectory()) {
        	  // Lister tous les fichiers dans le répertoire
              File[] fichiers = dossier.listFiles();
         // Vérifier si le répertoire contient des fichiers
            if (fichiers != null) {
            	  for (File fichier : fichiers) {
            		  try {
					mots1 = PatriciaTrie.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
					root=trie.ajout_successif(root, mots1);
						
            		  } catch (IOException e) {
						
            			  e.printStackTrace();
					}
      				
            		  
            	  }
            	  ObjectMapper mapper = new ObjectMapper();
   		       
				try {

	            	  String json = mapper.writeValueAsString(root);
					PatriciaTrie.ecrireSurfichier("Patricia_Shakespeare.json", json);
	     		       
				} catch (IOException e) {
					e.printStackTrace();
				}
    		      
            	  
            	  
            
            }
        
        }	
		
	}
	
	
}
