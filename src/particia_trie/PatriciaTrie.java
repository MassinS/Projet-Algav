package particia_trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatriciaTrie {
	/*
  1- Boucle principale et appels récursifs :
  La fonction insert est appelée récursivement jusqu'à ce que l’intégralité du mot soit insérée. 
  Cela se produit en O(n) appels dans le pire des cas, car on progresse caractère par caractère dans le mot.
  
  2-Comparaisons de caractères avec findCommonPrefix :

  À chaque appel récursif, la méthode findCommonPrefix effectue des comparaisons
  sur un maximum de O(n) caractères au total (répartis sur les appels récursifs).
  
  
   3- Manipulations des chaînes :
   Dans le pire des cas, des chaînes peuvent être scindées à chaque étape. Cela ajoute 
   O(n) pour les manipulations cumulées sur l’ensemble des appels récursifs.

   4-Recherches dans le map : 

   -Les recherches dans le map sont en O(1) et n’ajoutent pas de coût significatif.	
	
	Conclusion :
    La complexité totale de la fonction insert est linéaire par rapport à la longueur du mot, soit O(n)

	*/
	
	public void insert(PatriciaTrieNode node, String word) {
	    if (word.isEmpty()) {
	        node.isEndOfWord = true;
	        return;
	    }

	    char firstChar = word.charAt(0);
	    
	    
	    PatriciaTrieNode child = node.children.get(firstChar);

	    if (child != null) {
	        // Comparaison pour trouver le préfixe commun
	        String commonPrefix = findCommonPrefix(child.label, word);
	     // Comparaison pour déterminer si le préfixe commun est plus court
            
	        if (commonPrefix.length() < child.label.length()) {
	        	PatriciaTrieNode.incrementCompteur();
	            // Scénario de fractionnement du nœud
	            PatriciaTrieNode splitNode = new PatriciaTrieNode(commonPrefix);

	            // Ajouter le reste de l'ancien label comme enfant
	            splitNode.children.put(child.label.charAt(commonPrefix.length()), child);
	            child.label = child.label.substring(commonPrefix.length());

	            splitNode.isEndOfWord = false;

	            // Ajouter le nouveau nœud fractionné
	            node.children.put(firstChar, splitNode);

	            // Continuer l'insertion avec le reste du mot
	            insert(splitNode, word.substring(commonPrefix.length()));
	        } else {
	            insert(child, word.substring(commonPrefix.length()));
	        }
	    } else {
	        PatriciaTrieNode newNode = new PatriciaTrieNode(word);
	        newNode.isEndOfWord = true;
	        node.children.put(firstChar, newNode);
	    }
	}
                
    
	
	private  String findCommonPrefix(String str1, String str2) {
	    int minLength = Math.min(str1.length(), str2.length());
	    int i = 0;
	    while (i < minLength && str1.charAt(i) == str2.charAt(i)) {
	    	PatriciaTrieNode.incrementCompteur();
	        i++;
	    }
	    return str1.substring(0, i);
	}
	
	
	
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
	   if(node!=null) {
		   if ( node.isEndOfWord) {
				 i++;
				}
		   for (Map.Entry<Character, PatriciaTrieNode> entry : node.children.entrySet()) {
		        i += ComptageMots(entry.getValue());
		    }
	   }
		 
			
		
		return i;
		
	}
	
	

	
	public boolean Recherche(PatriciaTrieNode node, String word) {
	    // Vérifier si le mot est vide
	    if (word.isEmpty()) {
	    	
	        return node.isEndOfWord;
	    }

	    // Récupérer le premier caractère du mot
	    char firstChar = word.charAt(0);

	    // Rechercher le nœud correspondant dans les enfants
	    PatriciaTrieNode child = node.children.get(firstChar);
	    
		   
	    if (child == null) {
	        
	        return false;
	    }

	    // Vérifier si le mot commence par le label du nœud enfant
	    String label = child.label;
	    PatriciaTrieNode.incrementCompteur(); // Incrémenter le compteur pour la comparaison
	    if (word.startsWith(label)) {
	        // Continuer la recherche avec le reste du mot
	        return Recherche(child, word.substring(label.length()));
	    }

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
    
    
    
    public PatriciaTrieNode Suppression(PatriciaTrieNode node, String word) {
        if (node == null || word.isEmpty()) {
            return null; // Cas de base : arbre vide ou mot vide
        }
        PatriciaTrieNode.incrementCompteur();
        // Étape 1 : Vérifier si le label du nœud courant correspond au début du mot
        if (!word.startsWith(node.label)) {
            return node; // Si le mot ne correspond pas au chemin de ce nœud, il n'existe pas dans l'arbre
        }

        // Si le mot est plus long que le label actuel, continuer récursivement dans les enfants
        String remainingWord = word.substring(node.label.length());
       
        if (remainingWord.isEmpty()) {
            // Étape 2 : Cas où on atteint la fin du mot à supprimer
             
        	if (!node.children.isEmpty()) {
                // Cas 1 : Le nœud a des enfants, on met seulement isEndOfWord à false
                node.isEndOfWord = false;
            } else {
                // Cas 3 : Le nœud n'a pas d'enfants, on peut le supprimer en le retirant de son parent
                return null;
            }
        } else {
            // Étape 3 : Continuer la suppression dans les enfants
            char nextChar = remainingWord.charAt(0);
            PatriciaTrieNode child = node.children.get(nextChar);
           
            if (child == null) {
                return node; // Si l'enfant n'existe pas, le mot n'est pas dans l'arbre
            }

            // Appeler la suppression récursive sur l'enfant
            PatriciaTrieNode newChild = Suppression(child, remainingWord);
           
            if (newChild == null) {
                // Si l'enfant a été supprimé, le retirer du parent
                node.children.remove(nextChar);
            } else 
            	if (newChild.children.size() == 1 ) {
                // Cas 2 : L'enfant a un seul enfant et n'est pas une fin de mot
                Map.Entry<Character, PatriciaTrieNode> entry = newChild.children.entrySet().iterator().next();

                // Concaténer les labels
                newChild.label += entry.getValue().label;

                // Transférer les enfants de l'enfant unique
                newChild.children = entry.getValue().children;
            }
        }
   
        return node;
    }
    
    
    
      public Integer ProfondeurMoyenne(PatriciaTrieNode node) {
    	if(ComptageMots(node)==0) {
    		return 0;
    	}else {
    		return profondeurTotale(node,0)/ComptageMots(node);
        }
    	
    }
    	
    
    
   private Integer profondeurTotale(PatriciaTrieNode node, Integer profondeurActuelle) {

	   
	   int profondeurTotale = 0;

	    // Ajouter la profondeur actuelle si le nœud est la fin d'un mot
	   if(node!=null) {
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
   
   
   public List<String> ListeMots(PatriciaTrieNode Arbre) {
	    List<String> mots = new ArrayList<>();
	    if (Arbre != null) {
	    	
	    
	    ListeMotsRecursive(Arbre, "", mots);
	    return mots;
	    } else {
	    	return mots;
	    }
	}

   
	private void ListeMotsRecursive(PatriciaTrieNode Arbre, String prefix, List<String> mots) {
	    if (Arbre.isEndOfWord) {
	        mots.add(prefix + Arbre.label);
	    }

	    for (Map.Entry<Character, PatriciaTrieNode> entry : Arbre.children.entrySet()) {
	        ListeMotsRecursive(entry.getValue(), prefix + Arbre.label, mots);
	    }
	}
	
	
	public Integer Prefixe(PatriciaTrieNode node, String word) {
	    PatriciaTrieNode currentNode = node;
	    int index = 0; // Position actuelle dans le mot 'word'

	    // Parcours du Patricia Trie pour trouver le nœud correspondant au préfixe
	    while (index < word.length()) {
	        char currentChar = word.charAt(index);

	        // Vérifier si le caractère actuel existe parmi les enfants
	        if (currentNode.children.containsKey(currentChar)) {
	            PatriciaTrieNode childNode = currentNode.children.get(currentChar);

	            // Récupérer l'étiquette du nœud enfant
	            String label = childNode.label;
	            String commonPrefix = findCommonPrefix(word.substring(index), label);

	            // Avancer l'index selon la longueur du préfixe commun
	            index += commonPrefix.length();
	            currentNode = childNode;
	        } else {
	            // Si un caractère n'est pas trouvé, le préfixe n'existe pas
	            return 0;
	        }
	    }

	    // Une fois arrivé au nœud correspondant, compter les mots à partir de ce nœud
	    return ComptageMots(currentNode);
	}
     

	public PatriciaTrieNode FusionArbre ( PatriciaTrieNode Arbre1, PatriciaTrieNode Arbre2  ) {
		
		if ( Arbre1== null ) {
			return Arbre2;
		} else {
			if ( Arbre2==null ) {
				return Arbre1;
			}
		}
		
		List<String> Listemot1 = ListeMots(Arbre1);
		List<String> Listemot2 = ListeMots(Arbre2);
		
		Integer LengthListMot1= Listemot1.size();
		Integer LengthListMot2= Listemot2.size();
		
		if (LengthListMot1>LengthListMot2) {
			PatriciaTrieNode Arbre3 = Arbre1;
			for (String mot : Listemot2) {
				
				insert(Arbre3, mot);	
				
				}
				
				return Arbre3;
			
		} else {
			PatriciaTrieNode Arbre3= Arbre2;
              for (String mot : Listemot1) {
				
				insert(Arbre3, mot);	
				
				}
				
				return Arbre3;
		}
		
	
	}
    
	
	
	
	public PatriciaTrieNode ajout_successif( PatriciaTrieNode Arbre1, List<String> Liste  ) {
	
		 if (Liste.isEmpty()) {
		        return Arbre1; // Si la liste est vide, on retourne l'arbre construit
		    }

		 for (String element : Liste) {
			 insert(Arbre1, element);   
			 }
		 
		 return Arbre1;	
	
	}
	  
	
	
	public PatriciaTrieNode Suppression_successif(PatriciaTrieNode Arbre1 , List<String> Liste   ) {
	

		 if (Liste.isEmpty()) {
		        return Arbre1; // Si la liste est vide, on retourne l'arbre construit
		    }
		 
		
		 for (String element : Liste) {
			 Suppression(Arbre1, element);   
			 }
		 
		 return Arbre1;	
	}
	
	
	
	
	
	
/************************************************************************************************************************/	
	public static List<String> lireEtDecomposerFichier(String nomFichier) throws IOException {
	    List<String> mots = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
	        String ligne;
	        while ((ligne = br.readLine()) != null) {
	            String[]motsLigne = ligne.split("\\s+"); // Separation par un ou plusieurs espaces
	            for (String mot : motsLigne) {
	                mots.add(mot);
	            }
	        }
	    }
	    return mots;
}
	
	
	public static void ecrireSurfichier(String nomFichier,String contenu) throws IOException{
		
		StringBuilder sb = new StringBuilder();
	    for (char c : contenu.toCharArray()) {
	        if (c == '{' || c == '}' || c == ',') {
	        	sb.append(c);
	            sb.append('\n');
	        } else {
	            sb.append(c);
	        }
	    } //cela une question de beaute c'est tout pour rendre le fichier plus lisible des que il rencontre les trois caractere { } , il va faire un saut
	    String contenuFormate = sb.toString();
		try (BufferedWriter br = new BufferedWriter(new FileWriter(nomFichier))) {
	       br.write(contenuFormate);
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	
	
}
