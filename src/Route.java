import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Route {
    private static ArrayList<City> destinationCities = new ArrayList<>();
    private ArrayList<City> route;
    private double fitness;
    private double distance;

    public Route() {
        route = new ArrayList<>();
        distance = 0;
        fitness = 0;

        for (int i = 0; i < numberOfCities(); i++)
            route.add(null);
    }

    public void generateVariation() {
        for (int i = 0; i < numberOfCities(); i++)
            setCity(i, destinationCities.get(i));

        Collections.shuffle(route);
    }

    public City getCity(int tourPosition) {
        return route.get(tourPosition);
    }

    public void setCity(int tourPosition, City city) {
        route.set(tourPosition, city);
        fitness = 0;
        distance = 0;
    }

    public double getFitness() {
        if (fitness == 0)
            fitness = 1 / getDistance();

        return fitness;
    }

    public double getDistance() {
        if (distance == 0) {
            double routeDistance = 0;
            for (int i = 0; i < route.size(); i++) {
                City fromCity = getCity(i);
                City destinationCity;

                if (i + 1 < route.size())
                    destinationCity = getCity(i + 1);
                else
                    destinationCity = getCity(0);
                routeDistance += fromCity.distanceTo(destinationCity);
            }
            distance = routeDistance;
        }
        return distance;
    }

    public int routeSize() {
        return route.size();
    }

    public boolean containsCity(City city) {
        return route.contains(city);
    }

    public void reversePart(int pos1, int pos2) {
        ArrayList<City> revList = new ArrayList<>();

        if (pos1 > pos2) {
            for (int i = pos2; i <= pos1; i++)
                revList.add(route.get(i));

            Collections.reverse(revList);
            for (int j = 0; j < revList.size(); j++)
                route.set(pos2 + j, revList.get(j));

        } else {
            for (int i = pos1; i <= pos2; i++)
                revList.add(route.get(i));

            Collections.reverse(revList);
            for (int j = 0; j < revList.size(); j++)
                route.set(pos1 + j, revList.get(j));
        }
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < route.size(); i++) {
            result += getCity(i) + "=>";
        }
        return result;
    }

    public static void addCity() {
        City city = new City();
        destinationCities.add(city);
    }


    public static int numberOfCities() {
        return destinationCities.size();
    }

    private static class City {
        int x;
        int y;

        public City() {
            Random rand = new Random();

            this.x = rand.nextInt(200);
            this.y = rand.nextInt(200);
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public double distanceTo(City city) {
            int xDistance = Math.abs(getX() - city.getX());
            int yDistance = Math.abs(getY() - city.getY());
            double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

            return distance;
        }

        @Override
        public String toString() {
            return "(" + getX() + ", " + getY() + ")";
        }
    }
}
