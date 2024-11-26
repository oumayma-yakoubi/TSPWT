package org.example;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class NearestNeighborWithTime {

    public static void nearestNeighborWithTime(float[][] distMatrix, int C, int[][] timeWindowsMatrix, float[][] timeMatrix) {
        //int n = distMatrix.length;

        // float [][] timeMatrix = new float[C][C];

        // Vitesse (en km/h) == (km/ 60min)
//        float speed = 100;
//
//        for (int i = 0; i < C; i++) {
//            for (int j = 0; j < C; j++) {
//                // Calculer le temps = distance / vitesse
//                timeMatrix[i][j] =  (distMatrix[i][j] * 60 / speed);
//            }
//        }
//        for (float[] row : distMatrix) {
//            for (double value : row) {
//                System.out.printf("%.2f ", value); // Affichage avec 2 décimales
//            }
//            System.out.println();
//        }
//        System.out.println("La matrice Time Correspondante");
//        for (float[] row : timeMatrix) {
//            for (double value : row) {
//                System.out.printf("%.2f ", value); // Affichage avec 2 décimales
//            }
//            System.out.println();
//        }


        boolean[] visited = new boolean[C];
        List<Integer> path = new ArrayList<>();
        int currentCity = 0; // On suppose qu'on commence par la ville 0.
        float currentTime = 0;
        float totalDistance = 0;

        path.add(currentCity);
        visited[currentCity] = true;

        while(path.size() < C){
            List<Integer> availableCities = new ArrayList<>();
            int nextCity = -1;
            float minDistance = (Float.MAX_VALUE);

            for (int i = 0; i < C; i++){
                if (!visited[i]){  // Ville non visitée
                    double arrivalTime = currentTime + timeMatrix[currentCity][i];
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
                for (int i = 0; i < C; i++) {
                    if (!visited[i]) {
                        nextAvailableTime = Math.min(nextAvailableTime, timeWindowsMatrix[i][0]);
                    }
                }

                // Mettre à jour le temps courant pour attendre jusqu'à la prochaine fenêtre de temps
                //System.out.println("Attente jusqu'à : " + nextAvailableTime);
                currentTime = nextAvailableTime;
                continue; // Recommencer la boucle avec le nouveau temps courant
            }

            // Choisir la ville disponible la plus proche
            for (int city : availableCities){
                //System.out.println("ana f for dial availableCities" );
                if(distMatrix[currentCity][city] < minDistance){
                    //System.out.println("ana f if dial distMatrix o minDistance !!" );
                    minDistance = distMatrix[currentCity][city];
                    nextCity = city;
                }
            }
            if (nextCity == -1){ // Aucune ville n'est disponible.
                System.out.println("Aucune ville atteignable à partir de la ville :" + currentCity);
                break;
            }
            //System.out.println("neeext city" + nextCity );
            visited[nextCity] = true;
            path.add(nextCity);

            currentTime += (timeMatrix[currentCity][nextCity] + 15); // on suppose que le voyageur reste 15 min dans chaque ville
            //System.out.println("Thissssssss issss the new cuuurent time : " + currentTime);

            currentCity = nextCity;
            totalDistance += minDistance;

        }

        totalDistance +=distMatrix[currentCity][path.get(0)];

        System.out.println("Parcours : " + path);
        System.out.println("Distance totale : " + totalDistance);


//        return path;







        // List du parcours
//        ArrayList<Integer> route = new ArrayList<>();
//        route.add(currentCity);
//
//        // Distance totale du parcours
//        int totalDistance = 0;
//
//        // Trouve rla ville la plus proche à chaque étape
//        for (int i = 1; i < C; i++) {
//            // On initialise la valeur de la ville la plus proche par -1 et la distance minimale par un nombre grand
//            int nearestCity = -1;
//            int minDistance = Integer.MAX_VALUE;
//
//            // Trouver la ville non visitée la plus proche
//            for (int j = 0; j < C; j++) {
//                if (!visited[j] && distMatrix[currentCity][j] < minDistance) {
//                    nearestCity = j;
//                    minDistance = distMatrix[currentCity][j];
//                }
//            }
            // Marquer la ville comme visitée
//            visited[nearestCity] = true;
//
//            // Ajouter la ville au parcours
//            route.add(nearestCity);
//
//            // Ajouter la distance à la distance totale
//            totalDistance += minDistance;
//            currentCity = nearestCity;
//        }
//        // Retourner à la ville de départ
//        totalDistance +=distMatrix[currentCity][route.get(0)];
//
//        System.out.println("Parcours : " + route);
//        System.out.println("Distance totale : " + totalDistance);

        }


    }
