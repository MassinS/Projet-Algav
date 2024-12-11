package etude;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;
import com.fasterxml.jackson.databind.ObjectMapper;

import particia_trie.PatriciaTrie;

public class Hybride_trie_Encode_Shakespeare {

	public static void main(String[] args) {
		
		String cheminRepertoire = "Shakespeare";
		Tries_Hybrides Hybride=new Tries_Hybrides();
			
        File dossier = new File(cheminRepertoire);
        List<String> mots1;
        Noeud root = new Noeud();
		
        // Vérifier si c'est un répertoire valide
        if (dossier.isDirectory()) {
        	  // Lister tous les fichiers dans le répertoire
              File[] fichiers = dossier.listFiles();
         // Vérifier si le répertoire contient des fichiers
            if (fichiers != null) {
            	  for (File fichier : fichiers) {
            		  try {
					mots1 = PatriciaTrie.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
					root=Hybride.ajout_successif(mots1,root);
						
            		  } catch (IOException e) {
						
            			  e.printStackTrace();
					}
      				
            		  
            	  }
            	  
            	  ObjectMapper mapper = new ObjectMapper();
   		       
				try {

	            	  String json = mapper.writeValueAsString(root);
					PatriciaTrie.ecrireSurfichier("Hybride_Shakespeare.json", json);
	     		       
				} catch (IOException e) {
					e.printStackTrace();
				}
    		      
            	  
            	  
            
            }
        
        }	
		
	}
	
	
	
	
	
	
}
