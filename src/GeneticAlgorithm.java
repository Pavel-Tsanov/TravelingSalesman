import java.util.Random;
import java.util.Scanner;

public class GeneticAlgorithm {
    private static final double mutationRate = 0.02;

    public static RoutePopulation evolve(RoutePopulation pop) {
        RoutePopulation newPopulation = new RoutePopulation(pop.size(), false);

        newPopulation.saveRoute(0, pop.getFittestRoute());

        for (int i = 1; i < newPopulation.size(); i++) {

            Route parent1 = selection(pop);
            Route parent2 = selection(pop);

            Route child = crossover(parent1, parent2);

            newPopulation.saveRoute(i, child);
        }

        for (int i = 1; i < newPopulation.size(); i++)
            mutate(newPopulation.getRouteAtIndex(i));

        return newPopulation;
    }

    public static Route crossover(Route parent1, Route parent2) {
        Route child = new Route();
        Random rand = new Random();

        int startPos = rand.nextInt(parent1.routeSize());
        int endPos = rand.nextInt(parent1.routeSize());

        if (endPos < startPos) {
            int tmp = startPos;
            startPos = endPos;
            endPos = tmp;
        }

        for (int i = 0; i < child.routeSize(); i++) {
            if (i >= startPos && i <= endPos)
                child.setCity(i, parent1.getCity(i));
        }

        int ind = endPos + 1;
        int i = ind;
        while(ind < parent2.routeSize()){

            if (!child.containsCity(parent2.getCity(i))) {
                child.setCity(ind, parent2.getCity(i));
                ind++;
            }

            if(i == parent2.routeSize() - 1)
                i = -1;

            i++;
        }


        for (int k = 0; k < parent2.routeSize(); k++)
            if (!child.containsCity(parent2.getCity(k)))
                for (int j = 0; j < child.routeSize(); j++)
                    if (child.getCity(j) == null) {
                        child.setCity(j, parent2.getCity(k));
                        break;
                    }

        return child;
}

    private static void mutate(Route route) {
        Random rand = new Random();

        for (int pos1 = 0; pos1 < route.routeSize(); pos1++) {
            if (Math.random() < mutationRate) {
                int pos2 = rand.nextInt(route.routeSize());
                route.reversePart(pos1, pos2);
            }
        }
    }

    private static Route selection(RoutePopulation pop) {
        Random rand = new Random();
        int selectionSize = rand.nextInt(pop.size()) + 1;
        RoutePopulation subPop = new RoutePopulation(selectionSize, false);

        for (int i = 0; i < selectionSize; i++) {
            int randIndex = rand.nextInt(pop.size());
            subPop.saveRoute(i, pop.getRouteAtIndex(randIndex));
        }

        return subPop.getFittestRoute();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        if (N < 1 || N > 100)
            throw new IllegalArgumentException("Invalid argument");

        for (int i = 0; i < N; i++)
            Route.addCity();

        StringBuilder sb = new StringBuilder();
        RoutePopulation pop = new RoutePopulation(50, true);
        sb.append(String.format("First generation: %.2f\n", pop.getFittestRoute().getDistance()));

        pop = GeneticAlgorithm.evolve(pop);
        for (int i = 0; i < 100; i++) {
            if (i == 10 || i == 40 || i == 70)
                sb.append(String.format("%dth generation: %.2f\n", i, pop.getFittestRoute().getDistance()));
            pop = GeneticAlgorithm.evolve(pop);
        }

        sb.append(String.format("Last generation: %.2f\n", pop.getFittestRoute().getDistance()));
        System.out.println(sb.toString());
    }
}
