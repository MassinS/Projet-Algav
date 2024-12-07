package particia_trie;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Prefixe_pat {

	public static void main(String[] args) {
		//pour recuperer les fichier json
		 String nomFichier =args[0];
		 String prefix=args[1];
		 PatriciaTrie trie = new PatriciaTrie();
		 try {
				//transformer le fichier json en arbre
					ObjectMapper mapper = new ObjectMapper();
			        PatriciaTrieNode arbre1 = mapper.readValue(new File(nomFichier), PatriciaTrieNode.class);
			        int nbr_prefix=trie.Prefixe(arbre1, prefix);
			        PatriciaTrie.ecrireSurfichier("prefixe.txt",Integer.toString(nbr_prefix)); 
			 } catch (IOException e) {
			e.printStackTrace();
		}

	}

}
