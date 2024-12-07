package particia_trie;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class profondeurMoyenne_pat {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 PatriciaTrie trie = new PatriciaTrie();
		
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
					PatriciaTrieNode arbre1 = mapper.readValue(new File(nomFichier), PatriciaTrieNode.class);
			        int profondeur=trie.ProfondeurMoyenne(arbre1);
			        
			        PatriciaTrie.ecrireSurfichier("profondeur.txt",Integer.toString(profondeur));
			        
		                      		 
			 } catch (IOException e) {
			e.printStackTrace();
		} 

	}

}
