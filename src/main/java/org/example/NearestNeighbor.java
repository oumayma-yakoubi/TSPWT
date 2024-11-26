package org.example;

import java.util.ArrayList;

public class NearestNeighbor {

    public static void nearestNeighbor(float[][] distMatrix, int C ) {
        int currentCity = 0; // On suppose qu'on commence par la ville 0
        // Distance totale du parcours
        float totalDistance = 0;

        // List du parcours
        ArrayList<Integer> route = new ArrayList<>();
        route.add(currentCity);

        boolean[] visited = new boolean[C];
        visited[currentCity] = true;


        // Trouver la ville la plus proche à chaque étape
        for (int i = 1; i < C ; i++){
            // On initialise la valeur de la ville la plus proche par -1 et la distance minimale par un nombre grand
            int nearestCity = -1;
            float minDistance = Float.MAX_VALUE;

            // Trouver la ville non visitée la plus proche
            for(int j= 0; j < C; j++){
                int t = j+1;
                System.out.println(" I'm heere for the " + t + " Time" );
                if(!visited[j] && distMatrix[currentCity][j]<minDistance){
                    nearestCity = j;
                    minDistance =  distMatrix[currentCity][j];
                }
             }

            visited[nearestCity] = true; // Marquer la ville comme visitée
            route.add(nearestCity); // Ajouter la ville au parcours

            totalDistance += minDistance;  // Ajouter la distance à la distance totale
            currentCity = nearestCity;
        }
        // Retourner à la ville de départ
        totalDistance +=distMatrix[currentCity][route.get(0)];

        System.out.println("Parcours : " + route);
        System.out.println("Distance totale : " + totalDistance);

}

}
