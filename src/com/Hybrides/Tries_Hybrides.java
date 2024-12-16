package com.Hybrides;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import org.w3c.dom.Node;

import particia_trie.PatriciaTrieNode;


public class Tries_Hybrides {
//---------------------------Question 1.4 ---------------------------------
	
   Noeud racine;
   
   public Noeud TH_Ajout(String cle, Noeud courant) {
	    if (cle.isEmpty()) {
	        return courant;
	    }

	    if (EstVide(courant)) {
	        if (cle.length() == 1) {
	            return new Noeud(cle.charAt(0), TH_Vide(), TH_Vide(), TH_Vide(), true);
	        } else {
	            return new Noeud(cle.charAt(0), TH_Vide(), TH_Ajout(cle.substring(1), Eq(courant)), TH_Vide(), ValVide());
	        }
	    } else {
	        char p = cle.charAt(0); 
	        if (p < Rac(courant)) {
	        	Noeud.incrementCompteur();
	            return new Noeud(Rac(courant), TH_Ajout(cle, Inf(courant)), Eq(courant), Sup(courant), Val(courant));
	        }
	        if (p > Rac(courant)) {
	        	Noeud.incrementCompteur();
	            return new Noeud(Rac(courant), Inf(courant), Eq(courant), TH_Ajout(cle, Sup(courant)), Val(courant));
	        }
	        return new Noeud(Rac(courant), Inf(courant), TH_Ajout(cle.substring(1), Eq(courant)), Sup(courant), Val(courant));
	        
	        
	    }
	}

	
		
	public Noeud TH_Vide() {
		return null;
	}
	public boolean ValVide() {
		return false;
	}
	
	public boolean EstVide(Noeud courant) {
		if(courant==null) {
			return true;
		}else {
			return false;
		}
	}
	
	public Noeud Eq(Noeud A) {
		if(EstVide(A)) {
			return null;
		}else {
			return A.middle;
		}
		
	}
	
	public Noeud Sup(Noeud A) {
		if(EstVide(A)) {
			return null;
		}else {
			return A.right;
		}
	}
	
	public Noeud Inf(Noeud A) {
		if(EstVide(A)) {
			return null;
		}else {
			return A.left;
		}
	}
	
	public char Rac(Noeud A) {
		return A.character;
	}
	
	public boolean Val(Noeud A) {
		return A.isEndOfWord;
	}
	public boolean EstFeuille(Noeud arbre) {
		if(EstVide(arbre)) {
			return false;
		}else if(Val(arbre) && EstVide(Inf(arbre)) &&EstVide(Eq(arbre)) && EstVide(Sup(arbre))) {
			return true;
		}else {
			return false;
		}
		
	}
	public int ComptageFeuilles(Noeud arbre) {
	    if (EstVide(arbre)) {
	        return 0;
	    } else if (EstFeuille(arbre)) {
	        return 1;
	    } else {
	        return ComptageFeuilles(Inf(arbre))+ ComptageFeuilles(Eq(arbre)) + ComptageFeuilles(Sup(arbre));
	    }
	}
	
	
	
	
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
		File file=new File(nomFichier);
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
	
//---------------------------Question 1.5 ---------------------------------
	
	public Noeud ajout_successif(List<String> mots, Noeud courant) {
	    if (mots.isEmpty()) {
	        return courant; // Si la liste est vide, on retourne l'arbre construit
	    }

	    for (String element : mots) {
			courant = TH_Ajout(element,courant);   
			 }
	   
	    return courant;
	
	}
//---------------------------Question 2.6 ---------------------------------
	/*
	1-Dans le pire des cas : 2L
    -Recherche complète pour chaque caractère :

    Lorsqu'un caractère est absent ou mal placé, le parcours peut nécessiter jusqu’à 2 comparaisons par caractère :
    Une pour comparer dans l'arbre left ou right.
    Une autre pour passer dans l'arbre middle.

    -Nombre total de comparaisons :
    Si le mot a L caractères, et qu’on effectue jusqu’à 2 comparaisons pour chaque caractère dans un parcours complet, le total est 
    2L comparaisons.
    
    2-Dans le moyen des cas :L+log(n)
    -L : Comparaisons pour traverser les caractères de la clé
    Lors de la recherche ou de l'insertion d'une clé, chaque caractère de la clé doit être comparé pour déterminer si on continue dans le sous-arbre left, middle, ou right.
    Cela nécessite une comparaison par caractère pour les L caractères de la clé.

   -logn : Comparaisons pour localiser un nœud dans une structure binaire
    Le Trie Hybride est composé de sous-arbres qui suivent la logique des arbres binaires de recherche.
    Pour localiser un nœud (caractère) dans un sous-arbre, en moyenne, cela nécessite 
    logn comparaisons, où n est le nombre total de clés stockées dans le Trie.
	 */
	public boolean Recherche(Noeud arbre, String mot) {
	    if (EstVide(arbre)) {
	        return false;
	    } else {
	        if (mot.length() == 1) {
	            char c = mot.charAt(0);  
	            if (c < Rac(arbre)) {
	            	Noeud.incrementCompteur();
	                return Recherche(Inf(arbre), mot);
	            } else if (c > Rac(arbre)) {
	            	Noeud.incrementCompteur();
	                return Recherche(Sup(arbre), mot);
	            }
	                return Val(arbre); 
	            
	        } else {
	            char p = mot.charAt(0);  
	            if (p < Rac(arbre)) {
	            	Noeud.incrementCompteur();
	                return Recherche(Inf(arbre), mot);
	            } else if (p > Rac(arbre)) {
	            	Noeud.incrementCompteur();
	                return Recherche(Sup(arbre), mot);
	            }
	                return Recherche(Eq(arbre), mot.substring(1));
	            
	        }
	    }
	}

	/*
	 La complexité de cette fonction est linéaire par rapport au nombre de nœuds de l'arbre, soit O(n).
     Justification: Chaque nœud est visité une fois et une seule, et pour chaque nœud,
     on effectue un nombre constant d'opérations (comparaison avec null, incrémentation du compteur, appels récursifs).
	 */
	public int Comptage(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			int compte;
			if(Val(arbre)) {
				compte=1;
			}else {
				compte=0;
			}
			return compte+Comptage(Inf(arbre))+Comptage(Eq(arbre))+Comptage(Sup(arbre));
		}
	}
	/*
	 la complexité en nombre de comparaisons est O(n), où est le nombre total de nœuds dans l'arbre
	 Comme la fonction parcourt tous les nœuds de l'arbre, chaque nœud est vérifié une fois avec Val(arbre).
     Si l'arbre contient n nœuds, alors il y aura exactement n appels à Val(arbre).
	 
	 */
	public List<String> ListeMots(Noeud arbre,String prefix) {
	    List<String> mots = new ArrayList<>();
	    if (EstVide(arbre)) {
	        return mots; 
	    }
	    if(Val(arbre)) {
	    	mots.add(prefix+Rac(arbre));
	    }
	    	
	        mots.addAll(ListeMots(Inf(arbre),prefix));
	        mots.addAll(ListeMots(Eq(arbre),prefix+Rac(arbre)));
	        mots.addAll(ListeMots(Sup(arbre),prefix));
	        
	        return mots;
	    
	}
	/*
	 la complexité en nombre de comparaisons est O(n), où est le nombre total de nœuds dans l'arbre
	 car on doit visiter toujours tous les noeuds de l'arbre s'il est vide on retourne un 1 sinon on continue jusqu'a ce que
	 on atteint le nil d'un sous arbre donc on est oblige de parcourir tous les noeud et faire n comparaison.
	 */
	public int ComptageNil(Noeud arbre) {
		if(EstVide(arbre)) {
			return 1;
		}else {
			return ComptageNil(Inf(arbre))+ComptageNil(Eq(arbre))+ComptageNil(Sup(arbre));
		}
	}
	/*
	 En termes de comparaisons, la complexité est proportionnelle au nombre de nœuds  dans l'arbre.
     La complexité est donc O(n), car chaque nœud est visité une seule fois, et chaque nœud nécessite un nombre constant de comparaisons.
	 */
	public int Hauteur(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			return 1+Math.max(Math.max(Hauteur(Inf(arbre)), Hauteur(Eq(arbre))), Hauteur(Sup(arbre)));
		}
	}
	/*
	 En termes de comparaisons, la complexité est proportionnelle au nombre de nœuds  dans l'arbre.
	 Chaque nœud est visité une fois: La récursivité assure que chaque nœud est visité une fois et une seule.
     Traitement constant par nœud: Pour chaque nœud, les opérations effectuées (test si c'est une feuille, incrémentation de la profondeur, appels récursifs) prennent un temps constant.
     Nombre d'appels récursifs: Le nombre d'appels récursifs est égal au nombre de nœuds de l'arbre
	 */
	public int somme_profondeur_feuilles(Noeud arbre, int profondeur) {
	    if (EstVide(arbre)) {
	        return 0;
	    } else if (EstFeuille(arbre)) {
	        return profondeur;
	    } else {
	        return somme_profondeur_feuilles(Inf(arbre), profondeur + 1) +
	               somme_profondeur_feuilles(Eq(arbre), profondeur + 1) +
	               somme_profondeur_feuilles(Sup(arbre), profondeur + 1);
	    }
	}
	/*
	 puisque la complexite de profondeurMoyenne depend de la complexité des deux fonction somme_profondeur_feuilles
	 et ComptageFeuilles qui ont une complexite de O(N) en terme de comparaison ou N est le nombres des noeud dans l'arbre donc on peut 
	 deduire que la complexite de cette fonction son ordre de grandeur est O(N) 
	 */
	public int ProfondeurMoyenne(Noeud arbre) {
	    if (EstVide(arbre)) {
	        return 0;
	    } else {
	        int somme = somme_profondeur_feuilles(arbre, 0);
	        int nbFeuilles = ComptageFeuilles(arbre);
	        return somme / nbFeuilles;
	    }
	}
	/*
	 La complexité en nombre de comparaisons de la fonction nbr_end_word est linéaire, soit O(n), où n est le nombre de nœuds dans le Trie.
	 pourquoi O(N) car chaque nœud est visité une fois: La récursivité assure que chaque nœud est visité une fois et une seule.
	 */
	public int nbr_end_word(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			if(Val(arbre)) {
			return 1+nbr_end_word(Inf(arbre))+nbr_end_word(Eq(arbre))+nbr_end_word(Sup(arbre));
			}
			return nbr_end_word(Inf(arbre))+nbr_end_word(Eq(arbre))+nbr_end_word(Sup(arbre));
		}
	}
	/*fonction nbr_end_word pour recuperer le nombre de mots dans un noeud donne j'aurai besoin de cette fonction dans prefix
	 * car une fois on a parcouri tout le mot dans l'arbe donc il suffit de compte tous les mots qui suit pour savoir
	 * le nombre de mots dont le prefix est le mot en question
	 * /
	 */
	/*
	 ici dans le pire des cas c'est O(2L + log n) ou  2L (2 fois le nb max de caractères d’une clé)
     comparaisons et log(n) c'est par rapport au fonction nbr_end_word qui va prendre un seul chemin parmi les 3 du coup
     log(n) ou n represente le nombre de noeud car on doit faire tjr une compraraison a chaque noeud pour savoir s'il s'agit la fin d'un
     mot et on doit verifier ca pour tous les noeuds.
	 */
	public int Prefix(Noeud arbre,String mot) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			if(mot.length()==1) {
				if(mot.charAt(0)<Rac(arbre)) {
					Noeud.incrementCompteur();
					return Prefix(Inf(arbre), mot);
				}else if(mot.charAt(0)>Rac(arbre)) {
					Noeud.incrementCompteur();
					return Prefix(Sup(arbre), mot);
				}else {
					if(Val(arbre)) {
						return 1+nbr_end_word(Eq(arbre));
					}else {
						return nbr_end_word(Eq(arbre));
					}
				}
			}else {
				char p=mot.charAt(0);
				if(p<Rac(arbre)) {
					Noeud.incrementCompteur();
					return Prefix(Inf(arbre), mot);
				}else if(p>Rac(arbre)) {
					Noeud.incrementCompteur();
					return Prefix(Sup(arbre), mot);
				}else {
					return Prefix(Eq(arbre), mot.substring(1));
				}
			}
		}
	}
	/*
	 Dans le pire des cas, on doit parcourir tout le sous-arbre droit jusqu'à trouver la position d'insertion.
	 Cela implique de visiter tous les nœuds du sous-arbre droit, ce qui correspond à une complexité en O(h), 
	 où h est la hauteur de l'arbre.
	 */
	public Noeud bon_emplacement_sup(Noeud arbre,Noeud a_rajouter) {
		if(EstVide(arbre)) {
			return a_rajouter;
		}else {
			return new Noeud(Rac(arbre), Inf(arbre), Eq(arbre), bon_emplacement_sup(Sup(arbre), a_rajouter), Val(arbre));
		}
	}
	public Noeud supp(Noeud arbre,String mot) {
		if (mot.isEmpty()) {
	        return arbre;
	    }
		if(EstVide(arbre)) {
			return null;
		}else {
			char p=mot.charAt(0);
			  if(Prefix(arbre, ""+p)==1) {
				  if(Prefix(Sup(arbre), ""+p)==1) {
					  return new Noeud(Rac(arbre), Inf(arbre), Eq(arbre), supp(Sup(arbre), mot), Val(arbre));
				  }else if(Prefix(Inf(arbre), ""+p)==1) {
					  return new Noeud(Rac(arbre), supp(Inf(arbre), mot), Eq(arbre),Sup(arbre), Val(arbre));
				  }else {
				  if((!EstVide(Inf(arbre))) && (!EstVide(Sup(arbre)))){
					  return new Noeud(Rac(Inf(arbre)), Inf(Inf(arbre)), Eq(Inf(arbre)), bon_emplacement_sup(Sup(Inf(arbre)), Sup(arbre)), Val(Inf(arbre)));
				  }else if(!EstVide(Inf(arbre))) {
					  return new Noeud(Rac(Inf(arbre)), Inf(Inf(arbre)),Eq(Inf(arbre)),Sup(Inf(arbre)),Val(Inf(arbre)));
				  }else if(!EstVide(Sup(arbre))) {
					  return new Noeud(Rac(Sup(arbre)), Inf(Sup(arbre)),Eq(Sup(arbre)),Sup(Sup(arbre)),Val(Sup(arbre)));
				  }else {
					  return TH_Vide();
				  }
			  }
			  }else {
				  if(mot.length()==1) {
					  if(p<Rac(arbre)) {
						  Noeud.incrementCompteur();
						  return new Noeud(Rac(arbre),supp(Inf(arbre),mot),Eq(arbre),Sup(arbre),Val(arbre));
					  }else if(p>Rac(arbre)) {
						  Noeud.incrementCompteur();
						  return new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),supp(Sup(arbre),mot),Val(arbre));
					  }else {
						  return new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),Sup(arbre),false);
					  }
					  
				  }else {
					  if(p<Rac(arbre)) {
						  Noeud.incrementCompteur();
						  return new Noeud(Rac(arbre),supp(Inf(arbre),mot),Eq(arbre),Sup(arbre),Val(arbre));
					  }else if(p>Rac(arbre)) {
						  Noeud.incrementCompteur();
						  return new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),supp(Sup(arbre),mot),Val(arbre));
					  }else {
						  return new Noeud(Rac(arbre),Inf(arbre),supp(Eq(arbre),mot.substring(1)),Sup(arbre),Val(arbre));
					  }
				  }
			  }
		}
	}
	
	public Noeud Suppression(Noeud arbre,String mot) {
				if(Recherche(arbre, mot)) {
					return supp(arbre, mot);
				}else {
					return arbre;
				}
			  
			
	}
	
	public Noeud suppression_successif(List<String> mots, Noeud courant) {
		if (mots.isEmpty()) {
	        return courant; // Si la liste est vide, on retourne l'arbre construit
	    }
		
		
		 for (String element : mots) {
			courant=  Suppression(courant, element);   
			 }
		
	    return courant;
	}
	
	
	
	/*
	 la complexite de cette fonction est O(N*2*L) ou N represente la taille de la liste mots ou en d'autre terme le nombre de mots
	 dans l'arbre 2 et L le nombre max de caractères d’une clé ou un mot
	 */
	public Noeud fusion(Noeud arbre1,Noeud arbre2) {
		Noeud arbre=new Noeud();
		if(EstVide(arbre1)) {
			return arbre2;
		}else if(EstVide(arbre2)) {
			return arbre1;
		}else {
			List<String> mots1=ListeMots(arbre1, "");
			int mots_arbre1=mots1.size();
			List<String> mots2=ListeMots(arbre2, "");
			int mots_arbre2=mots2.size();
			if(mots_arbre1<=mots_arbre2) {
				arbre=ajout_successif(mots1, arbre2);
			}else {
				arbre=ajout_successif(mots2, arbre1);
			}
			
			return arbre;
		}
		
	}
//---------------------------Question 3.8 ---------------------------------
	/*
	 cette fonction calcul la hauteur d'un noeud mais en prenant compte seulement les sous arbre gauche et droite.
	 */
	public int taille_g_d(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else if(EstVide(Inf(arbre)) && EstVide(Sup(arbre))){
			return 0;
		}else if(!EstVide(Inf(arbre)) && !EstVide(Sup(arbre))) {
			return 1+Math.max(taille_g_d(Inf(arbre)),taille_g_d(Sup(arbre)));
		}
		else {
			return 1+taille_g_d(Inf(arbre))+taille_g_d(Sup(arbre));
		}
	}
	/*
	  Un arbre est considéré comme équilibré si la différence de hauteur entre ses sous-arbres gauche et droit est inférieure
	  a une valeur qui est entre 1 et -1 tout en prenant compte l'arbre au milieu c'est a dire il doit respecte aussi cette contraint par exemple
	  si je suis a la racine je verifier les sous arbre gauche et droit en calculant facteur d'equilibrage meme si je trouve entre 
	  1 et -1 je dois verifier le noeud au milieu si son facteur d'equilibrage est entre -1 et 1 donc on soit suivre recurersivement
	  ce processus, donc le seuil est de ±1. Cela signifie que si le facteur d'équilibre d'un nœud est supérieur à 1 
	  en valeur absolue, l'arbre est considéré comme déséquilibré et une rotation doit être effectuée pour le rééquilibrer.
	 */
	public int facteur_equilibrage(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else if(!EstVide(Inf(arbre)) && !EstVide(Sup(arbre))) {
			return (taille_g_d(Inf(arbre)))-(taille_g_d(Sup(arbre)));
		}
		else if(EstVide(Inf(arbre)) && !EstVide(Sup(arbre))) {
			return taille_g_d(Inf(arbre))-(taille_g_d(Sup(arbre))+1);
		}
		else {
			return (1+taille_g_d(Inf(arbre)))-taille_g_d(Sup(arbre));
		}
		
	}
	
	
	public Noeud RG(Noeud arbre) {
		Noeud inter=new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),Inf(Sup(arbre)),Val(arbre));
		return new Noeud(Rac(Sup(arbre)),inter,Eq(Sup(arbre)),Sup(Sup(arbre)),Val(Sup(arbre)));
	}
	public Noeud RD(Noeud arbre) {
		Noeud inter=new Noeud(Rac(arbre),Sup(Inf(arbre)),Eq(arbre),Sup(arbre),Val(arbre));
		return new Noeud(Rac(Inf(arbre)),Inf(Inf(arbre)),Eq(Inf(arbre)),inter,Val(Inf(arbre)));
	}
	public Noeud RGG(Noeud arbre) {
		return null;
	}
	public Noeud RDD(Noeud arbre) {
		return null;
	}
	public Noeud RDG(Noeud arbre) {
		Noeud arbre1=new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),RD(Sup(arbre)),Val(arbre));
		return RG(arbre1);
	}
	public Noeud RGD(Noeud arbre) {
		Noeud arbre1=new Noeud(Rac(arbre),RG(Inf(arbre)),Eq(arbre),Sup(arbre),Val(arbre));
		return RD(arbre1);
	}
	/*
	 
	 */
	public Noeud equilibrage(Noeud arbre) {
		if(EstVide(arbre) || (facteur_equilibrage(arbre)<2 && facteur_equilibrage(arbre)>-2)) {
			if(facteur_equilibrage(Eq(arbre))<2 && facteur_equilibrage(Eq(arbre))>-2) {
				return arbre;	
			}else {
				return new Noeud(Rac(arbre),Inf(arbre),equilibrage(Eq(arbre)),Sup(arbre),Val(arbre));
			}
			
		}else if(facteur_equilibrage(arbre)==-2) {
			if(facteur_equilibrage(Sup(arbre))==1) {
				return RDG(new Noeud(Rac(arbre),Inf(arbre),equilibrage(Eq(arbre)),Sup(arbre),Val(arbre)));
			}else {
				return RG(new Noeud(Rac(arbre),Inf(arbre),equilibrage(Eq(arbre)),Sup(arbre),Val(arbre)));
			}
		}else if(facteur_equilibrage(arbre)==2) {
			if(facteur_equilibrage(Inf(arbre))==-1) {
					return RGD(new Noeud(Rac(arbre),Inf(arbre),equilibrage(Eq(arbre)),Sup(arbre),Val(arbre)));
			}else {
				return RD(new Noeud(Rac(arbre),Inf(arbre),equilibrage(Eq(arbre)),Sup(arbre),Val(arbre)));
			}
		}else {
		return equilibrage(new Noeud(Rac(arbre),equilibrage(Inf(arbre)),equilibrage(Eq(arbre)),equilibrage(Sup(arbre)),Val(arbre)));
		}
	}
	
	public Noeud ajout_equilibrage(List<String> mots, Noeud courant) {
	    if (mots.isEmpty()) {
	        return courant; // Si la liste est vide, on retourne l'arbre construit
	    }

	    String mot = mots.get(0);
	    mots.remove(0); // On supprime le premier element de la liste
	    Noeud arbre = TH_Ajout(mot, courant);
	    return equilibrage(ajout_equilibrage(mots, arbre));
	}
	
	public static void main(String[] args)  {
		Tries_Hybrides tt=new Tries_Hybrides();
		 List<String> mots1;
        try {
        	mots1 = tt.lireEtDecomposerFichier("mots.txt");
        	int anis=mots1.size();
            Noeud nn=tt.ajout_successif(mots1, null);
	        System.out.println(anis);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

    }
	
	
	}
