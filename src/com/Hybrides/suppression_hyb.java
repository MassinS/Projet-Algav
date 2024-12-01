package com.Hybrides;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class suppression_hyb {

	public static void main(String[] args) {
		List<String> mots;
		 String nomFichier =args[0];//pour recuperer le nom de fichier en question 
		 Tries_Hybrides tt=new Tries_Hybrides();
			try {
				//transformer le fichier json en arbre
				ObjectMapper mapper = new ObjectMapper();
		        Noeud arbre = mapper.readValue(new File("trie.json"), Noeud.class);
		        //lire le fichier qui contient les mots a supprimer
				mots = tt.lireEtDecomposerFichier(nomFichier);
		        Noeud nn=tt.suppression_successif(mots, arbre);
		     // Serialiser l'arbre en JSON
		        ObjectMapper mappere = new ObjectMapper();
		        String json = mappere.writeValueAsString(nn);
		        tt.ecrireSurfichier("trie.json", json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

	}

}
