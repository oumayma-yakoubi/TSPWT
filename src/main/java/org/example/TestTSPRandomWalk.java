package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTSPRandomWalk {
    public static void testRandomWalk(float[][] distMatrix, int numCities, int[][] timeWindowsMatrix , float[][] timeMatrix, float pnoise) {

        boolean[] visited = new boolean[numCities];
        List<Integer> path = new ArrayList<>();
        int currentCity = 0;
        float currentTime = 0;
        float totalDistance = 0;
        Random random = new Random();

        path.add(currentCity);
        visited[currentCity] = true;

        List<Integer> citiesWithTW = new ArrayList<>();


        // Trouver les villes avec des contraintes de temps.
        for(int i = 0 ; i < numCities; i++){
            if(timeWindowsMatrix[i][0] != 0 || timeWindowsMatrix[i][1] != 1440){
                citiesWithTW.add(i);
            }
        }

        while(path.size() < numCities){
            List<Integer> availableCities = new ArrayList<>();
            int nextCity = -1;
            float minDistance = Float.MAX_VALUE;

            for(int city = 0 ; city < numCities; city++){
                if(!visited[city]){
                    float arrivalTime = currentTime + timeMatrix[currentCity][city];
                    int openTime = timeWindowsMatrix[city][0];
                    //int closeTime = timeWindowsMatrix[city][1];

                    if(arrivalTime >= openTime){
                        availableCities.add(city);
                    }
                }
            }
            List<Integer> availableWithTimeWindows = new ArrayList<>();
            for(int city : availableCities){
                if (citiesWithTW.contains(city)){
                    availableWithTimeWindows.add(city);
                }
            }

            if(!availableWithTimeWindows.isEmpty()){
                int minWidth = Integer.MAX_VALUE;
                for(int city : availableWithTimeWindows){
                    int width = timeWindowsMatrix[city][1] - timeWindowsMatrix[city][0];
                    if(width < minWidth){
                        nextCity = city;
                        minWidth = width;
                        minDistance= distMatrix[currentCity][nextCity];
                    }
                }
            }else{
                if(pnoise < random.nextFloat()) {
                    nextCity = availableCities.get(random.nextInt(availableCities.size()));
                    minDistance = distMatrix[currentCity][nextCity];
                } else{
                    for(int city : availableCities){
                        if(distMatrix[currentCity][city] < minDistance){
                            nextCity = city;
                            minDistance = distMatrix[currentCity][city];
                        }
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
            if (nextCity == -1){ // Aucune ville n'est disponible.
                System.out.println("Aucune ville atteignable à partir de la ville :" + currentCity);
                break;
            }

            visited[nextCity] = true;
            path.add(nextCity);
            currentTime += timeMatrix[currentCity][nextCity];

            currentCity = nextCity;
            totalDistance +=minDistance;
        }
        totalDistance += distMatrix[currentCity][path.get(0)];

        System.out.println("Parcours : " + path);
        System.out.println("Distance Totale : " + totalDistance);


    }
}

