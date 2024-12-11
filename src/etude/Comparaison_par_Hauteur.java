package etude;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
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

public class Comparaison_par_Hauteur {

	
	
	public static void main(String[] args) {
		 String cheminRepertoire = "Shakespeare";
		 
		 
		 Tries_Hybrides tt=new Tries_Hybrides();
		 PatriciaTrie trie = new PatriciaTrie();
	     
		 XYSeries seriesHybride = new XYSeries("Courbe d'arbre hybride");
		 XYSeries seriesPatricia = new XYSeries("Courbe de Patricia Trie");
       
		 seriesHybride.add(0,0);
		 seriesPatricia.add(0,0);
		 
		 
		 List<String> mots1;
		 
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
            		
    				mots1 = Tries_Hybrides.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
    				int y=mots1.size();
    				
    				Noeud hybride = tt.ajout_successif(mots1, null);
    				PatriciaTrieNode Patricia = trie.ajout_successif(null, mots1);
                 
    				  long startTimeHybride = System.nanoTime();
                    tt.Hauteur(hybride);
                    long endTimeHybride = System.nanoTime();
                    long durationHybride = endTimeHybride - startTimeHybride;
                    
                    
                    seriesHybride.add(durationHybride / 1_000_000.0, y);

                    
                    long startTimePatricia = System.nanoTime();
                    
                    trie.Hauteur(Patricia);
                    
                    long endTimePatricia = System.nanoTime();
                    long durationPatricia = endTimePatricia - startTimePatricia;

                    
                    seriesPatricia.add(durationPatricia / 1_000_000.0, y);
    		     
                    
    			} catch (IOException e) {
    				e.printStackTrace();
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
    dataset.addSeries(seriesPatricia);
    
    // Ajouter les séries à une collection
    XYSeriesCollection dataset1 = new XYSeriesCollection();
    dataset1.addSeries(seriesHybride);
    
    
    
    // Créer le graphique
    JFreeChart chart = ChartFactory.createXYLineChart(
            "la fonction de Hauteur dans la structure patricia", // Titre
            "Temps d'exécution (en millisecondes)", // Axe X
            "Nombre de mots", // Axe Y
            dataset // Données
    );

    
    JFreeChart chart1 = ChartFactory.createXYLineChart(
            "la fonction de Hauteur dans la structure hybride", // Titre
            "Temps d'exécution (en millisecondes)", // Axe X
            "Nombre de mots", // Axe Y
            dataset1 // Données
    );
    
    
    // La couleur Blue pour l'arbre Patricia.
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.getRenderer().setSeriesPaint(0, Color.BLUE); 
    
    
    // La couleur rouge pour l'arbre Hybride.
    XYPlot plot1 = (XYPlot) chart1.getPlot();
    plot1.getRenderer().setSeriesPaint(0, Color.RED);
    
    
    // Afficher le graphique dans une fenêtre Swing
    JFrame frame = new JFrame("Le graphe pour un arbre patricia.");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new ChartPanel(chart));
    frame.pack();
    frame.setVisible(true);

    
    // Afficher le graphique dans une fenêtre Swing
    JFrame frame1 = new JFrame("Le graphe pour un arbre hybride.");
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.add(new ChartPanel(chart1));
    frame1.pack();
    frame1.setVisible(true);
      
    
	}
	
	
	
	
	
	
	
}
