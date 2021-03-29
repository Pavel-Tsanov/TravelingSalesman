public class RoutePopulation {
    private Route[] routes;

    public RoutePopulation(int populationSize, boolean toInit) {
        routes = new Route[populationSize];

        if (toInit)
            for (int i = 0; i < routes.length; i++) {
                Route addRoute = new Route();
                addRoute.generateVariation();
                routes[i] = addRoute;
            }
    }

    public Route getFittestRoute() {
        Route fittest = routes[0];

        for (int i = 1; i < routes.length; i++) {
            if (fittest.getFitness() < routes[i].getFitness())
                fittest = routes[i];
        }

        return fittest;
    }

    public void saveRoute(int index, Route route) {
        routes[index] = route;
    }

    public Route getRouteAtIndex(int index) {
        return routes[index];
    }

    public int size() {
        return routes.length;
    }

}
