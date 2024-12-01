package com.Hybrides;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ListeMots_hyb {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
			        Noeud arbre1 = mapper.readValue(new File(nomFichier), Noeud.class);
			        List<String> mots=tt.ListeMots(arbre1, "");
			        // enregistrer l'ensemble des mots dans fichier mots.txt
			        File file=new File("mots.txt");
					StringBuilder sb = new StringBuilder();
					for (String mot:mots) {
						sb.append(mot);
						sb.append('\n');
					}
					String contenuFormate = sb.toString();
					try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
				       br.write(contenuFormate);
				    }catch(IOException e) {
				    	e.printStackTrace();
				    }
			 } catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
