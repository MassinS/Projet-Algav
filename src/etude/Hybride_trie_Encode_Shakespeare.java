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
            	  long startTimeHybride = System.nanoTime();
                  
            	  for (File fichier : fichiers) {
            		  try {
					mots1 = PatriciaTrie.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
					root=Hybride.ajout_successif(mots1,root);
						
            		  } catch (IOException e) {
						
            			  e.printStackTrace();
					}
      				
            		  
            	  }
            	  long endTimeHybride = System.nanoTime();
                  
            	  
            	  
            	  ObjectMapper mapper = new ObjectMapper();
   		       
				try {

	            	  String json = mapper.writeValueAsString(root);
					PatriciaTrie.ecrireSurfichier("Hybride_Shakespeare.json", json);
	     		       
				} catch (IOException e) {
					e.printStackTrace();
				}
    		      
				
            	//  Hybride.TH_Ajout("Salut", root);
            	
				  Noeud.resetCompteur();
            	  
				  long durationHybride = endTimeHybride - startTimeHybride;
                 
            	  long StartTimeAjout   = System.nanoTime();
              	  Hybride.TH_Ajout("salut", root);
              	  long EndTimeAjout   = System.nanoTime();
            	  long durationAjout = EndTimeAjout - StartTimeAjout;
              	  
            	  System.out.println("Nombre de comparaison pour ajouter le mot salut : " + Noeud.getCompteur() );
                  
            	  long StartTimeHauteur   = System.nanoTime();
              	  Hybride.Hauteur(root);
              	  long EndTimeHauteur  = System.nanoTime();
            	  long durationHauteur = EndTimeHauteur - StartTimeHauteur;
              	  
            	  
            	  System.out.println("Ajout total de Sheakspeare " + durationHybride / 1_000_000.0 + " ms");
              	  System.out.println("Ajout de mot salut : " + durationAjout/ 1_000_000.0 + " ms");
              	  System.out.println("L'hauteur : " + durationHauteur/ 1_000_000.0 + " ms");
                 
              	  System.out.println(Hybride.Hauteur(root));
              	  System.out.println(Hybride.ProfondeurMoyenne(root) );
                      
              	  System.out.println("Nombre de comparaison : " + Noeud.getCompteur() );
                  
                  
					
					
            
            }
        
        }	
		
	}
	
	
	
	
	
	
}
