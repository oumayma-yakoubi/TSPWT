package org.example;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.*;
import org.chocosolver.solver.variables.*;

import javax.imageio.IIOException;
import javax.xml.crypto.Data;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        // Nombre de villes
//        int C = 3;
//
//        // Matrice des distances entre les villes
//        float[][] distMatrix = {
//                {0, 2, 9},
//                {2, 0, 6},
//                {9, 6, 0},
//        };
//
//        //NearestNeighbor.nearestNeighbor(distMatrix, C );
//
//
//        int[][] timeWindowsMatrix = {
//                {0, 1440},
//                {20, 940},
//                {0, 1440 },
//        };

        // Résolution du problème avec l'algorithme Nearest Neighbor

        String filePath = "C:\\Users\\Oumayma\\Tsp-choco\\src\\Data\\n201_instance1.txt";
        try{
            DataProcessor dataProcessor = new DataProcessor(filePath);

            int numCities = dataProcessor.getNumCities();
            float[][] distMatrix = dataProcessor.getDistMatrix();
            int[][] timeWindowsMatrix = dataProcessor.getTimeWindowsMatrix();

            float [][] timeMatrix = new float[numCities][numCities];

            // Vitesse (en km/h) == (km/ 60min)
            float speed = 100;
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    // Calculer le temps = distance / vitesse
                    timeMatrix[i][j] =  (distMatrix[i][j] * 60 / speed);
                }
            }

//            System.out.println("La matrice Distance");
//
//            for (float[] row : distMatrix) {
//                for (double value : row) {
//                    System.out.printf("%.2f ", value); // Affichage avec 2 décimales
//                }
//                System.out.println();
//            }
//            System.out.println("La matrice Time Correspondante");
//            for (float[] row : timeMatrix) {
//                for (double value : row) {
//                    System.out.printf("%.2f ", value); // Affichage avec 2 décimales
//                }
//                System.out.println();
//            }

            float pnoise_exploitation = 1;
            System.out.println("################## Exploitation uniquement #####################");
            TestTSPRandomWalk.testRandomWalk(distMatrix, numCities, timeWindowsMatrix, timeMatrix, pnoise_exploitation);

            float pnoise_exploit = 0.3f;
            System.out.println("################## Exploration & Exploitation(++) #####################");
            TestTSPRandomWalk.testRandomWalk(distMatrix, numCities, timeWindowsMatrix, timeMatrix, pnoise_exploit );

            float pnoise_explor = 0.6f;
            System.out.println("################## Exploration(++) & Exploitation #####################");
            TestTSPRandomWalk.testRandomWalk(distMatrix, numCities, timeWindowsMatrix, timeMatrix, pnoise_explor);

        } catch (IOException e){
            System.err.println("Erreur lors de la lecture du fichier");
        }


    }
}