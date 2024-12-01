package particia_trie;

import java.io.File;
import java.io.IOException;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;
import com.fasterxml.jackson.databind.ObjectMapper;

public class fusion_pat {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 String nomFichier2=args[1];
		 PatriciaTrie trie = new PatriciaTrie();
		//cree un objet de type Tries_Hybrides pour utiliser la fonction ecrireSurfichier qui permet de creer un fichier un contenu donne.
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
				//transformer les fichiers json en arbre
					ObjectMapper mapper = new ObjectMapper();
					PatriciaTrieNode arbre1 = mapper.readValue(new File(nomFichier), PatriciaTrieNode.class);
			        PatriciaTrieNode arbre2 = mapper.readValue(new File(nomFichier2), PatriciaTrieNode.class);
			        PatriciaTrieNode arbre=trie.FusionArbre(arbre1, arbre2);
			        // Serialiser l'arbre en JSON
			        String json = mapper.writeValueAsString(arbre);
			        tt.ecrireSurfichier("pat.json", json);
			        
				 
			 } catch (IOException e) {
			e.printStackTrace();
		}

	}

}
