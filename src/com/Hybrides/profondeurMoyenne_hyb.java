package com.Hybrides;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class profondeurMoyenne_hyb {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
			        Noeud arbre1 = mapper.readValue(new File(nomFichier), Noeud.class);
			        int profondeur=tt.ProfondeurMoyenne(arbre1);
			        
			        tt.ecrireSurfichier("profondeur.txt",Integer.toString(profondeur));
			        
				 
			 } catch (IOException e) {
			e.printStackTrace();
		}  
	}

}
