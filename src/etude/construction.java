package etude;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.Hybrides.Noeud;
import com.Hybrides.Tries_Hybrides;


public class construction {

	public static void main(String[] args) {
		String cheminRepertoire = "Shakespeare";
		 Tries_Hybrides tt=new Tries_Hybrides();
		 XYSeries series = new XYSeries("Courbe d'arbre hybride");
		 series.add(0,0);
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
        				mots1 = tt.lireEtDecomposerFichier("Shakespeare/"+fichier.getName());
        				int y=mots1.size();
        				long startTime = System.nanoTime();
        		        Noeud hybride=tt.ajout_successif(mots1, null);
        		        long endTime = System.nanoTime();
        		        long duration = endTime - startTime;
        		        
        		        series.add(duration/1_000_000.0,y);
        		        
        		     
        		        
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
                }
            } else {
                System.out.println("Le répertoire est vide ou inaccessible.");
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
        }
     // Ajouter la série à une collection
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Créer le graphique
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Construction des deux structures", // Titre
                "temps d'execution(en millisecondes)", // Axe X
                "nombre de mots", // Axe Y
                dataset // Données
        );

        // Afficher le graphique dans une fenêtre Swing
        JFrame frame = new JFrame("Comparaison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);

	}

}
