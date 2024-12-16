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
		int y=0;
        // Vérifier si c'est un répertoire valide
        if (dossier.isDirectory()) {
        	  // Lister tous les fichiers dans le répertoire
              File[] fichiers = dossier.listFiles();
         // Vérifier si le répertoire contient des fichiers
            if (fichiers != null) {
            	long startTimeHybride = System.nanoTime();
                
            	  for (File fichier : fichiers) {
            		  try {
					mots1 = PatriciaTrie.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
					root=trie.ajout_successif(root, mots1);
					   y=y+mots1.size();
            		  } catch (IOException e) {
						
            			  e.printStackTrace();
					}
      				
            		  
            	  }
            	  long endTimeHybride = System.nanoTime();
                  
            	  
            	  ObjectMapper mapper = new ObjectMapper();
   		       
				try {

	            	  String json = mapper.writeValueAsString(root);
					PatriciaTrie.ecrireSurfichier("Patricia_Shakespeare.json", json);
	     		       
				} catch (IOException e) {
					e.printStackTrace();
				}
    		      
			   
				System.out.println("Nombre de comparaison pour la construction de toute l'oeuvre de Sheakspeare : " + PatriciaTrieNode.getCompteur());
	          	    
				
				PatriciaTrieNode.resetCompteur();
				// le temps nécessaire pour calculer le temps d'insertion du mot Salut
			  long durationPatricia = endTimeHybride - startTimeHybride;
              long StartTimeAjout   = System.nanoTime();
          	  trie.insert(root, "salut");
          	  long EndTimeAjout   = System.nanoTime();
        	  long durationAjout = EndTimeAjout - StartTimeAjout;
        	  
        	  System.out.println("Nombre de comparaison pour ajouter le mot salut : " + PatriciaTrieNode.getCompteur() );
              
        	  
        	  System.out.println("Nombre totale des mots de l'oeuvre de sheakspeare :"+  y);
    			
          	  System.out.println("Temps nécessaire pou l'ajout total de Sheakspeare " + durationPatricia / 1_000_000.0 + " ms");
          	  System.out.println("Temps nécessaire pou l'ajout de mot salut :  " + durationAjout/ 1_000_000.0 + " ms");
          	 
          	  System.out.println("L'hauteur : "+ trie.Hauteur(root));
              
          	  System.out.println("La profondeur : "+ trie.ProfondeurMoyenne(root) );
              
          	  
          	  // Nombre de comparaison pour retrouver le mot measure dans une arbre patricia contient toute l'oeuvre de Sheakspeare 
			  PatriciaTrieNode.resetCompteur();
			  trie.Recherche(root, "measure");
			  System.out.println("Le nombre de comparaison dans la recherche du mot measure : "+ PatriciaTrieNode.getCompteur() );
              
			  
			  PatriciaTrieNode.resetCompteur();
			  trie.Suppression(root, "measure");
			  System.out.println("Le nombre de comparaison pour supprimer le mot measure : "+ PatriciaTrieNode.getCompteur() );
	              
			  
            } 
        
        }	
		
	}
	
	
}
