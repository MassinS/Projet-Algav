package com.Hybrides;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class fusion_hyb {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 String nomFichier2=args[1];
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
			//transformer les fichiers json en arbre
				ObjectMapper mapper = new ObjectMapper();
		        Noeud arbre1 = mapper.readValue(new File(nomFichier), Noeud.class);
		        Noeud arbre2 = mapper.readValue(new File(nomFichier2), Noeud.class);
		        Noeud arbre=tt.fusion(arbre1, arbre2);
		        // Serialiser l'arbre en JSON
		        String json = mapper.writeValueAsString(arbre);
		        tt.ecrireSurfichier("trie.json", json);
		        
			 
		 } catch (IOException e) {
		e.printStackTrace();
	}  
	}

}
