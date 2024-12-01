package com.Hybrides;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.Hybrides.Tries_Hybrides;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Hybrides.Tries_Hybrides;

public class inserer_hyb {
	 public static void main(String[] args) {
		 List<String> mots;
		 String nomFichier =args[0];//pour recuperer le nom de fichier en question
		 File dossier = new File("."); // "." represente le repertoire courant
		 File[] fichiers = dossier.listFiles((dir, name) -> name.matches(".*trie.*\\.json"));//recuperer tout les fichier qui ont un nom trie
		 Tries_Hybrides tt=new Tries_Hybrides();
			try {
				mots = tt.lireEtDecomposerFichier(nomFichier);
		        Noeud nn=tt.ajout_successif(mots, null);
		     // Serialiser l'arbre en JSON
		        ObjectMapper mapper = new ObjectMapper();
		        String json = mapper.writeValueAsString(nn);
		        if(fichiers.length==0) {
		        	tt.ecrireSurfichier("trie.json", json);
		        }else {
		        	tt.ecrireSurfichier("trie"+fichiers.length+".json", json);
		        }
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	 }
}
