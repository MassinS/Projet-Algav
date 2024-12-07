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

	
	/*
	 La fonction parcourt l'intégralité de l'arbre pour compter les nœuds marqués comme fin de mot.
	 Complexité temporelle : O(n), car chaque nœud est visité une fois.
	 */
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
	

	/*
      La complexité est : O(n)
	  
	*/
	
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

    
	
	/*
	 La fonction calcule la hauteur en explorant tous les chemins de l'arbre.
	 O(n), car chaque nœud est visité une fois.
	 */
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
    
    
  /* La complexité est de O(L) où L est la longeur de mot à supprimer */
    public PatriciaTrieNode Suppression(PatriciaTrieNode node, String word) {
        
    	 if (word.isEmpty()) {

    		 node.isEndOfWord = false;
    		 if (node.children.isEmpty()) {
                 return null;  
             }
             return node;
             
 	    }
    	   
    	 char firstChar = word.charAt(0);
 	     PatriciaTrieNode child = node.children.get(firstChar);

 	    if (child == null) {
	        // Aucun chemin correspondant
	        return node;
	    }
 	   String label = child.label;
 	   if (word.startsWith(label)) { 
	        // Le mot commence par le label, on continue avec le reste du mot
	        return Suppression(child, word.substring(label.length()));
	    }
 	  return node;
    	
    }
    
    

    /*
    La fonction calcule la profondeur pour chaque mot (parcours complet) et divise par le nombre de mots.
    O(n), car chaque nœud est visité une fois
    */
    
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
   
   /*
    La fonction vérifie pour chaque nœud si ses enfants sont vides, ce qui nécessite un parcours complet
    O(n), car chaque nœud est visité une fois
    */
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
   
   /*
       Complexité: O(N) 
    */
   
   public List<String> ListeMots(PatriciaTrieNode Arbre) {
	    List<String> mots = new ArrayList<>();
	    ListeMotsRecursive(Arbre, "", mots);
	    return mots;
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
	    return countWordsFromNode(currentNode);
	}
     
	
	
	private int countWordsFromNode(PatriciaTrieNode node) {
	    int count = 0;

	    // Si ce nœud marque la fin d'un mot, on incrémente le compteur
	    if (node.isEndOfWord) {
	        count++;
	    }

	    // Parcourir récursivement tous les enfants
	    for (PatriciaTrieNode child : node.children.values()) {
	        count += countWordsFromNode(child);
	    }

	    return count;
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
    
	
	
	
	
	
	
	
	/* La complexité est mesuré par :  
	
	 
	 la complexité d'une insertion d'un mot est de : O(L) où L est la longeur de mot à insèrer 
	 et le d est la profondeur de l'arbre
	 donc si on aura plusieurs mots à insèrer donc 
	 on aura L1,L2,L3.....Li
	 donc la complexité sera : La somme de (Li) de 1 jusqu'a m où m est le nombre de mot à insèrer.
	 
	 */
	
	public PatriciaTrieNode ajout_successif( PatriciaTrieNode Arbre1, List<String> Liste  ) {
		
		 for (String element : Liste) { 
		 insert(Arbre1, element);   
		 }
		 return Arbre1;
	
	}
	  
	
	/*
	 La complexité est mesuré par :
	 - La complexité de suppression d'un mot est : O(L) où L est la taille de mot à supprimer 
	 donc la complexité totale sera : la somme de Li de 1 jusqu'a m où m est le nombre de mot à supprimer
	 
	 */
	public PatriciaTrieNode Suppression_successif(PatriciaTrieNode Arbre1 , List<String> Liste   ) {
	
		 for (String element : Liste) {
			 Suppression(Arbre1, element);   
			 }
		 
		 return Arbre1;	
	}
	
	
	
	public boolean isEmpty(PatriciaTrieNode Arbre1) {
	    return Arbre1.label.isEmpty() && Arbre1.children.isEmpty();
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
