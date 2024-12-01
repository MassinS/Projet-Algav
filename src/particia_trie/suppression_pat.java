package particia_trie;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;
import com.fasterxml.jackson.databind.ObjectMapper;

public class suppression_pat {

	public static void main(String[] args) {
		List<String> mots;
		 String nomFichier =args[0];//pour recuperer le nom de fichier en question 
		 PatriciaTrie trie = new PatriciaTrie();
		 //cree un objet de type Tries_Hybrides pour utiliser la fonction ecrireSurfichier qui permet de creer un fichier un contenu donne.
		 Tries_Hybrides tt=new Tries_Hybrides();
			/*try {
				//transformer le fichier json en arbre
				ObjectMapper mapper = new ObjectMapper();
				PatriciaTrieNode arbre = mapper.readValue(new File("pat.json"), PatriciaTrieNode.class);
		        //lire le fichier qui contient les mots a supprimer
				mots = tt.lireEtDecomposerFichier(nomFichier);
				PatriciaTrieNode nn=trie.suppression_successif(mots, arbre);
		     // Serialiser l'arbre en JSON
		        ObjectMapper mappere = new ObjectMapper();
		        String json = mappere.writeValueAsString(nn);
		        tt.ecrireSurfichier("pat.json", json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */

	}

}
