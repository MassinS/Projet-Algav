package particia_trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatriciaTrie {
 
	
	public  void insert(PatriciaTrieNode node, String word) {
		 
		if (word.isEmpty()) {
		        node.isEndOfWord = true;
		        return;
		    }
		
		char firstChar = word.charAt(0);
	    PatriciaTrieNode child = node.children.get(firstChar);
	    
	    
	    if (child != null) {
	        // Correspondance trouvée : appel récursif avec le nœud enfant et le reste du mot
	    	
	        String commonPrefix = findCommonPrefix(child.label, word);
	        if (commonPrefix.length() < child.label.length()) {
	            // Si le préfixe commun est plus court que le label du nœud
	        
	        	// Imaginons que j'ai déjà le mot "balltze" qui est insèré dans l'arbre 
			    // Et ensuie je veux insèrer le mot "bal" et le prefixe commun est le mot "bal" donc on aura 
			    // le noeud "bal" qu'est un mot  et son fils le reste de balltze : "ltze"  
			    
		    	
	        	// On cree l'arbre avec le commonprefix= "bal"
	        	PatriciaTrieNode splitNode = new PatriciaTrieNode(commonPrefix);
	        	
	        	// On ajoute le fils "ltze"
	        	 splitNode.children.put(child.label.charAt(commonPrefix.length()), child);

	            
	        	 
	        	 child.label = child.label.substring(commonPrefix.length());

	        	 splitNode.isEndOfWord = false;
	            
	            node.children.put(firstChar, splitNode); 

	         // On appelle récursivement le insert pour continuer l'insertion de reste de mot
	         // ici c'est le mot vide qu'est un cas déjà traité au-dessus   
	            
	            insert(splitNode, word.substring(commonPrefix.length()) );
	                                                                       
	         
	            
	        } else {
	        	// le cas où  le prefix commun est le meme de la label donc j'insere le reste du mot dans ce noeud
	         insert(child, word.substring(commonPrefix.length()) );
	        } 
	        
	    } else {
	    	// Le cas où le premier caractère n'est pas trouvé dans l'arbre donc on doit le créé et insèrer tout le mot
	    	PatriciaTrieNode newNode = new PatriciaTrieNode(word);
	    	newNode.isEndOfWord = true;
	    	node.children.put(firstChar, newNode);
	    }
	    
	    
	}
	
	
	private  String findCommonPrefix(String str1, String str2) {
	    int minLength = Math.min(str1.length(), str2.length());
	    int i = 0;
	    while (i < minLength && str1.charAt(i) == str2.charAt(i)) {
	        i++;
	    }
	    return str1.substring(0, i);
	}
	
	
	
	// Afficher tout les mots de l'arbre 
	// l'idée ici est d'entrer une chaine de caractere et chaque label qu'on croise on concatene
	// lorsque j'arrive au moment où isEndOfWord est true ici je peux afficher le mot car il est complet
	public void AfficherLesMots(PatriciaTrieNode node, String prefix) {
        if (node.isEndOfWord) {
            System.out.println(prefix + node.label);
        }
        for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
        	AfficherLesMots(entry.getValue(), prefix + node.label);
        }
    }

	
	public Integer ComptageMots(PatriciaTrieNode node) {
	   int i=0;
	   
		if ( node.isEndOfWord) {
		 i++;
		} 
			
		for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
	        i += ComptageMots(entry.getValue());
	    }
		return i;
		
	}
	

	public boolean Recherche(PatriciaTrieNode node, String word) {
	    if (word.isEmpty()) {
	        return node.isEndOfWord;
	    }

	    char firstChar = word.charAt(0);
	    PatriciaTrieNode child = node.children.get(firstChar);

	    if (child == null) {
	        // Aucun chemin correspondant
	        return false;
	    }

	    String label = child.label;
	    if (word.startsWith(label)) { 
	        // Le mot commence par le label, on continue avec le reste du mot
	        return Recherche(child, word.substring(label.length()));
	    }

	    // Le label ne correspond pas au début du mot 
	    return false;
	}

    
    public Integer Hauteur(PatriciaTrieNode node) {
		
    	if (node == null || node.children.isEmpty()) {
            return 0; 
        }
    	int maxChildHeight = 0;
        // L'idée ici est de calculer la hauteur d'arbre en commencant du bas et ensuite en remontant
    	for (PatriciaTrieNode child : node.children.values()) {
            maxChildHeight = Math.max(maxChildHeight, Hauteur(child));
        }
    	
    	return 1 + maxChildHeight;

    }
    

    public PatriciaTrieNode Suppression(PatriciaTrieNode node, String word, int index) {
        
    	// L'idee est de parcourir l'arbre et trouvé le mot souhaité supprimer 
    	// Si on trouve le mot on mets le isEndOfWord en false 
    	// On vérifie si il a des children 
    	// S'il a pas de children on peut supprimer directement le noeud 
    	// Sinon s'il a beaucoup de children on mets seulement isEndOfWord en false 
    	// Sinon s'il a un seul children on recupère son label et le concatene avec le label de noeud 
    	// Et ses children deviens les children de noeud 
    	
    	// Cas de base : fin du mot
        if (index == word.length()) {
            node.isEndOfWord = false;

            // Si le nœud n'a pas d'enfants, il peut être supprimé
            if (node.children.isEmpty()) {
                return null;  
            }
            return node;
        }

        char currentChar = word.charAt(index);
        PatriciaTrieNode child = node.children.get(currentChar);

        // Appel récursif pour continuer à parcourir l'arbre
        PatriciaTrieNode updatedChild = Suppression(child, word, index + child.label.length());

        if (updatedChild == null) {
            // Supprime le lien vers l'enfant
            node.children.remove(currentChar);

            // Si le nœud courant devient inutile (ni fin de mot, ni parent), le signaler pour suppression
            if (node.children.isEmpty() && !node.isEndOfWord) {
                return null;
            }
        } else if (node.children.size() == 1 && !node.isEndOfWord) {
            // Fusion avec l'unique enfant
            Map.Entry<Character, PatriciaTrieNode> entry = node.children.entrySet().iterator().next();
            PatriciaTrieNode singleChild = entry.getValue();

            node.label += singleChild.label;
            node.isEndOfWord = singleChild.isEndOfWord;
            node.children = singleChild.children;
        }
      // Si le les children de noued ont plus de un seul children 
        return node;
    }


    public Integer ProfondeurMoyenne(PatriciaTrieNode node) {
    	return profondeurTotale(node,0)/ComptageMots(node);
    }
    
    
   private Integer profondeurTotale(PatriciaTrieNode node, Integer profondeurActuelle) {
	
	   
	   int profondeurTotale = 0;

	    // Ajouter la profondeur actuelle si le nœud est la fin d'un mot
	    if (node.isEndOfWord) {
	        profondeurTotale += profondeurActuelle;
	    }

	    // Parcourir les enfants
	    for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
	        profondeurTotale += profondeurTotale(
	            entry.getValue(), 
	            profondeurActuelle + entry.getValue().label.length()
	        );
	    }

	    return profondeurTotale;
	 		
   }
   
   
   public Integer ComptageNil( PatriciaTrieNode node ) {
	   
	   int i=0;
	   
		if ( node.children.isEmpty()) {
		 i++;
		} 
			
		for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
	        i += ComptageNil(entry.getValue());
	    }
		return i;
		
		
   }
   
   
   public List<String> ListeMots(PatriciaTrieNode node) {
	    List<String> mots = new ArrayList<>();
	    ListeMotsRecursive(node, "", mots);
	    return mots;
	}

	private void ListeMotsRecursive(PatriciaTrieNode node, String prefix, List<String> mots) {
	    if (node.isEndOfWord) {
	        mots.add(prefix + node.label);
	    }

	    for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
	        ListeMotsRecursive(entry.getValue(), prefix + node.label, mots);
	    }
	}
	
	
	
    
	public Integer Prefixe(PatriciaTrieNode node, String word) {
	
		int i=0;
		List<String> Listemot = ListeMots(node);
		for (String mot : Listemot) {
		String CommonPrefix = findCommonPrefix(word, mot); 
			if(word==CommonPrefix) {
				i++;
			}
		}
		
		return i;
	}
    
	
	public PatriciaTrieNode FusionArbre ( PatriciaTrieNode Arbre1, PatriciaTrieNode Arbre2  ) {
		
		List<String> Listemot = ListeMots(Arbre2);
		
		for (String mot : Listemot) {
		
		insert(Arbre1, mot);	
		
		}
		
		return Arbre1;
	
	
	}
    
	
	
	
}
