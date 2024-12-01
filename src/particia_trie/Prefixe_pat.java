package particia_trie;

import java.io.File;
import java.io.IOException;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Prefixe_pat {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 String prefix=args[1];
		 PatriciaTrie trie = new PatriciaTrie();
		 //cree un objet de type Tries_Hybrides pour utiliser la fonction ecrireSurfichier qui permet de creer un fichier un contenu donne.
		 Tries_Hybrides tt=new Tries_Hybrides();
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
			        PatriciaTrieNode arbre1 = mapper.readValue(new File(nomFichier), PatriciaTrieNode.class);
			        int nbr_prefix=trie.Prefixe(arbre1, prefix);
			        tt.ecrireSurfichier("prefixe.txt",Integer.toString(nbr_prefix)); 
			 } catch (IOException e) {
			e.printStackTrace();
		}

	}

}
