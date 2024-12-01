package com.Hybrides;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Prefixe_hyb {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 String prefix=args[1];
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
			        Noeud arbre1 = mapper.readValue(new File(nomFichier), Noeud.class);
			        int nbr_prefix=tt.Prefix(arbre1, prefix);
			        tt.ecrireSurfichier("prefixe.txt",Integer.toString(nbr_prefix)); 
			 } catch (IOException e) {
			e.printStackTrace();
		}  

	}

}
