package etude;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;

import particia_trie.PatriciaTrie;
import particia_trie.PatriciaTrieNode;


public class Comparaison_par_Ajout_successif {

	     public static void main(String[] args) {
		 String cheminRepertoire = "Shakespeare";
		 Tries_Hybrides tt=new Tries_Hybrides();
		 PatriciaTrie trie = new PatriciaTrie();
	     
		 XYSeries seriesHybride = new XYSeries("Courbe d'arbre hybride");
		 XYSeries seriesPatricia = new XYSeries("Courbe de Patricia Trie");

		 
		 seriesHybride.add(0,0);
		 seriesPatricia.add(0,0);
		 
		 List<String> mots1;
		 List<String> mots2;
		 
		
		 // Créer un objet File pour le répertoire
        File dossier = new File(cheminRepertoire);

        // Vérifier si c'est un répertoire valide
        if (dossier.isDirectory()) {
            // Lister tous les fichiers dans le répertoire
            File[] fichiers = dossier.listFiles();
        
            // Vérifier si le répertoire contient des fichiers
            if (fichiers != null) {
                // Parcourir chaque fichier
                for (File fichier : fichiers) {
                	try {
                		
                		Noeud hybride = new Noeud();
                        
                		mots1 = lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
        				int y=mots1.size();
        		
        				
        				long startTimeHybride = System.nanoTime();
                        
        				hybride = tt.ajout_successif(mots1, hybride);
                        
                        long endTimeHybride = System.nanoTime();
                        long durationHybride = endTimeHybride - startTimeHybride;
                        
                        seriesHybride.add( y, durationHybride / 1_000_000.0);
                      
                          
                      
        		     
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
                	
                	
                	try {
                		
                		mots2 = lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
        				int y=mots2.size();
        		
        				PatriciaTrieNode root = new PatriciaTrieNode("");
                        
        				PatriciaTrieNode.resetCompteur();
        				
                        long startTimePatricia = System.nanoTime();
                        
                        
                        root= trie.ajout_successif(root, mots2);
                        
                        
                        long endTimePatricia = System.nanoTime();
                        
                        long durationPatricia = endTimePatricia - startTimePatricia;
                        
                        
                        seriesPatricia.add( y, durationPatricia / 1_000_000.0);
                        
                         System.out.println("Nombre de comparaison : "+ PatriciaTrieNode.getCompteur());
                         System.out.println("Profondeur de patricia : "+ trie.ProfondeurMoyenne(root) );
                         System.out.println("Nombre de mots  : "+ trie.ComptageMots(root) );
                         
						
					} catch (Exception e) {
					
					
					}
               
                	
                	
                }
            } else {
                System.out.println("Le répertoire est vide ou inaccessible.");
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
        }
        
        
        
        // Ajouter les séries à une collection
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesHybride);
        dataset.addSeries(seriesPatricia);
       
        
        
        // Créer le graphique
        JFreeChart chart = ChartFactory.createXYLineChart(
                "la construction de la structure patricia et hybride", // Titre
                "Nombre de mots",
                "Temps d'exécution (en millisecondes)", // Axe X
                 // Axe Y
                dataset // Données
        );

     
        
        
        // La couleur Blue pour l'arbre Patricia.
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.RED); // Hybride
        plot.getRenderer().setSeriesPaint(1, Color.BLUE); // Hybride
        
       
        
        // Afficher le graphique dans une fenêtre Swing
        JFrame frame = new JFrame("Le graphe de comparaison pour l'arbre patricia et hybride .");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);

     
        
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


}
