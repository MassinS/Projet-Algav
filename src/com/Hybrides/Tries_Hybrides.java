package com.Hybrides;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tries_Hybrides {
//---------------------------Question 1.4 ---------------------------------
	
   Noeud racine;
   
	public Noeud TH_Ajout(String cle,Noeud courant) {
		if (cle.isEmpty()) {
	        return courant;
	    }
		if(EstVide(courant)) {
		 if(cle.length() == 1) {
			 return new Noeud(cle.charAt(0),TH_Vide(),TH_Vide(),TH_Vide(), true);
		 }else {
			 return new Noeud(cle.charAt(0),TH_Vide(),TH_Ajout(cle.substring(1), Eq(courant)),TH_Vide(),ValVide());
		 }
		}else {
		  char p=cle.charAt(0);
		  if(p<Rac(courant)) {
			  return new Noeud(Rac(courant),TH_Ajout(cle, Inf(courant)),Eq(courant),Sup(courant),Val(courant));
		  }else if(p>Rac(courant)) {
			  return new Noeud(Rac(courant),Inf(courant),Eq(courant),TH_Ajout(cle, Sup(courant)),Val(courant));
		  }else {
			  return new Noeud(Rac(courant),Inf(courant),TH_Ajout(cle.substring(1), Eq(courant)),Sup(courant),Val(courant));
		  }
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
		 if(Val(arbre) && EstVide(Inf(arbre)) &&EstVide(Eq(arbre)) && EstVide(Sup(arbre))) {
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

	    String mot = mots.get(0);
	    mots.remove(0); // On supprime le premier element de la liste
	    Noeud arbre = TH_Ajout(mot, courant);
	    return ajout_successif(mots, arbre);
	}
//---------------------------Question 2.6 ---------------------------------
	
	public boolean Recherche(Noeud arbre,String mot) {
		if(EstVide(arbre)) {
				return false;
		}else {
			if(mot.length()==1) {
				if(mot.charAt(0)<Rac(arbre)) {
					return Recherche(Inf(arbre), mot);
				}else if(mot.charAt(0)>Rac(arbre)) {
					return Recherche(Sup(arbre), mot);
				}else {
					if(Val(arbre)) {
						return true;	
					}else {
						return false;
					}
				}
			}else {
				char p=mot.charAt(0);
				if(p<Rac(arbre)) {
					return Recherche(Inf(arbre), mot);
				}else if(p>Rac(arbre)) {
					return Recherche(Sup(arbre), mot);
				}else {
					return Recherche(Eq(arbre), mot.substring(1));
				}
			}
		}
	}
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
	public int ComptageNil(Noeud arbre) {
		if(EstVide(arbre)) {
			return 1;
		}else {
			return ComptageNil(Inf(arbre))+ComptageNil(Eq(arbre))+ComptageNil(Sup(arbre));
		}
	}
	public int Hauteur(Noeud arbre) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			return 1+Math.max(Math.max(Hauteur(Inf(arbre)), Hauteur(Eq(arbre))), Hauteur(Sup(arbre)));
		}
	}
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
	public int ProfondeurMoyenne(Noeud arbre) {
	    if (EstVide(arbre)) {
	        return 0;
	    } else {
	        int somme = somme_profondeur_feuilles(arbre, 0);
	        int nbFeuilles = ComptageFeuilles(arbre);
	        return somme / nbFeuilles;
	    }
	}
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
	
	public int Prefix(Noeud arbre,String mot) {
		if(EstVide(arbre)) {
			return 0;
		}else {
			if(mot.length()==1) {
				if(mot.charAt(0)<Rac(arbre)) {
					return Prefix(Inf(arbre), mot);
				}else if(mot.charAt(0)>Rac(arbre)) {
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
					return Prefix(Inf(arbre), mot);
				}else if(p>Rac(arbre)) {
					return Prefix(Sup(arbre), mot);
				}else {
					return Prefix(Eq(arbre), mot.substring(1));
				}
			}
		}
	}
	public Noeud bon_emplacement_sup(Noeud arbre,Noeud a_rajouter) {
		if(EstVide(arbre)) {
			return a_rajouter;
		}else {
			return new Noeud(Rac(arbre), Inf(arbre), Eq(arbre), bon_emplacement_sup(Sup(arbre), a_rajouter), Val(arbre));
		}
	}
	
	public Noeud Suppression(Noeud arbre,String mot) {
		if(EstVide(arbre)) {
			 return arbre;
			}else {
				if(Recherche(arbre, mot)) {
					char p=mot.charAt(0);
					  if(Prefix(arbre, ""+p)==1) {
						  if(Prefix(Sup(arbre), ""+p)==1) {
							  return new Noeud(Rac(arbre), Inf(arbre), Eq(arbre), Suppression(Sup(arbre), mot), Val(arbre));
						  }else if(Prefix(Inf(arbre), ""+p)==1) {
							  return new Noeud(Rac(arbre), Suppression(Inf(arbre), mot), Eq(arbre),Sup(arbre), Val(arbre));
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
							  return new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),Sup(arbre),false);
						  }else {
							  if(p<Rac(arbre)) {
								  return new Noeud(Rac(arbre),Suppression(Inf(arbre),mot),Eq(arbre),Sup(arbre),Val(arbre));
							  }else if(p>Rac(arbre)) {
								  return new Noeud(Rac(arbre),Inf(arbre),Eq(arbre),Suppression(Sup(arbre),mot),Val(arbre));
							  }else {
								  return new Noeud(Rac(arbre),Inf(arbre),Suppression(Eq(arbre),mot.substring(1)),Sup(arbre),Val(arbre));
							  }
						  }
					  }
				}else {
					return arbre;
				}
			  
			}
	}
	public Noeud suppression_successif(List<String> mots, Noeud courant) {
		if (mots.isEmpty()) {
	        return courant; // Si la liste est vide, on retourne l'arbre construit
	    }

	    String mot = mots.get(0);
	    mots.remove(0); // On supprime le premier element de la liste
	    Noeud arbre = Suppression(courant,mot);
	    return suppression_successif(mots, arbre);
	}
	public Noeud fusion(Noeud arbre1,Noeud arbre2) {
		List<String> mots=ListeMots(arbre2, "");
		Noeud arbre=ajout_successif(mots, arbre1);
		return arbre;
	}
//---------------------------Question 3.8 ---------------------------------
	
	
	public static void main(String[] args)  {
	   

    }
	
	
	}
