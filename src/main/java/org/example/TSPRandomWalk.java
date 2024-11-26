package org.example;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class TSPRandomWalk {

    public static void tspRandomWalk(float[][] distMatrix, int numCities, int[][] timeWindowsMatrix , float[][] timeMatrix, float pnoise) {

        boolean[] visited = new boolean[numCities];
        List<Integer> path = new ArrayList<>();
        int currentCity = 0; // On suppose qu'on commence par la ville 0.
        float currentTime = 0;
        float totalDistance = 0;
        Random random = new Random();

        path.add(currentCity);
        visited[currentCity] = true;

        while(path.size() < numCities){
            List<Integer> availableCities = new ArrayList<>();
            int nextCity = -1;
            float minDistance = (Float.MAX_VALUE);

            // Identifier les villes atteignables
            for (int i = 0; i < numCities; i++){
                if (!visited[i]){  // Ville non visitée
                    float arrivalTime = currentTime + timeMatrix[currentCity][i];
                    //System.out.println("arrivalTime : " + arrivalTime );

                    int openTime = timeWindowsMatrix[i][0];
                    int closeTime = timeWindowsMatrix[i][1];

                    // Vérifions si la ville est atteignable dans la fenêtre de temps
                    if (arrivalTime >= openTime && arrivalTime + 15 < closeTime){ // Ici, on vérifie s'il va avoir du temps suffisant avant la fermeture du
                        //System.out.println("Available cities : " + i );
                        availableCities.add(i);
                    }
                }
            }
            if (availableCities.isEmpty()) {
                // Aucune ville disponible, attendre jusqu'à la prochaine fenêtre
                float nextAvailableTime = Float.MAX_VALUE;
                for (int i = 0; i < numCities; i++) {
                    if (!visited[i]) {
                        nextAvailableTime = Math.min(nextAvailableTime, timeWindowsMatrix[i][0]);
                    }
                }

                // Mettre à jour le temps courant pour attendre jusqu'à la prochaine fenêtre de temps
                //System.out.println("Attente jusqu'à : " + nextAvailableTime);
                currentTime = nextAvailableTime;
                continue; // Recommencer la boucle avec le nouveau temps courant
            }

            if (pnoise < random.nextFloat()){
                nextCity = availableCities.get(random.nextInt(availableCities.size()));
                minDistance = distMatrix[currentCity][nextCity];
            } else {
                 for (int city : availableCities){
                     if(distMatrix[currentCity][city] < minDistance){
                         minDistance = distMatrix[currentCity][city];
                         nextCity = city;
                     }
                 }
            }
            if (nextCity == -1){ // Aucune ville n'est disponible.
                //System.out.println("Aucune ville atteignable à partir de la ville :" + currentCity);
                break;
            }
            //System.out.println("neeext city" + nextCity );
            visited[nextCity] = true;
            path.add(nextCity);

            currentTime += (timeMatrix[currentCity][nextCity] + 5); // On suppose que le voyageur reste 5 min dans chaque ville
            //System.out.println("Thissssssss issss the new cuuurent time : " + currentTime);

            currentCity = nextCity;
            totalDistance += minDistance;
        }
        totalDistance +=distMatrix[currentCity][path.get(0)];

        System.out.println("Parcours : " + path);
        System.out.println("Distance totale : " + totalDistance);
    }
}
