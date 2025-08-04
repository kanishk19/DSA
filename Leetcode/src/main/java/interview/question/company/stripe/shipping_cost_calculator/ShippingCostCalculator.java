package interview.question.company.stripe.shipping_cost_calculator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShippingCostCalculator {
    //US,UK,UPS,5:US,CA,FedEx,3:CA,UK,DHL,7"
    private static Map<String, Map<String, Double>> directCostMap = new HashMap<>();
    private static Set<String> allCitiesPool = new HashSet<>();
    private static final String DELIMITER = ":";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args){
        String shippingDetailsString = "US,UK,UPS,5:US,CA,FedEx,3:CA,UK,DHL,7";
        parseShippingDetails(shippingDetailsString);
        findDirectCost("US", "UK");
        findDirectCost("CA", "UK");
        findDirectCost("UK", "US");
    }

    public static void parseShippingDetails(String shippingDetailsString){
        boolean validationResult = validateShippingDetails(shippingDetailsString);
        String[] shippingDetails = shippingDetailsString.split(DELIMITER);

        for(String shippingDetail : shippingDetails){
            parseSingleShippingDetail(shippingDetail);
        }
    }

//    public int getMinCostWithOneIntermediate(String source, String destination) {
//        int minCost = Integer.MAX_VALUE;
//        System.out.println(String.format("\nSearching for min cost %s -> Z -> %s (one intermediate step)...", source, destination));
//
//        // Iterate through all known locations to consider each as a potential intermediate point (Z)
//        for (String intermediateLocation : allCitiesPool) {
//            // An intermediate step cannot be the source or destination itself.
//            // This prevents trivial or self-loop paths from being considered as "intermediate".
//            if (intermediateLocation.equals(source) || intermediateLocation.equals(destination)) {
//                continue;
//            }
//
//            // Get the cost for the first leg: Source -> Intermediate (X -> Z)
//            int costXZ = findDirectCost(source, intermediateLocation);
//
//            // If a direct path from Source to Intermediate exists
//            if (costXZ != Integer.MAX_VALUE) {
//                // Get the cost for the second leg: Intermediate -> Destination (Z -> Y)
//                int costZY = getDirectCost(intermediateLocation, destination);
//
//                // If a direct path from Intermediate to Destination exists
//                if (costZY != Integer.MAX_VALUE) {
//                    // Calculate the total cost for this specific two-step route (X -> Z -> Y)
//                    int currentTotalCost = costXZ + costZY;
//                    System.out.println(String.format("  Found route: %s -> %s -> %s. Total cost: %d + %d = %d",
//                            source, intermediateLocation, destination, costXZ, costZY, currentTotalCost));
//                    // Update the minimum cost found so far
//                    minCost = Math.min(minCost, currentTotalCost);
//                }
//            }
//        }
//
//        if (minCost == Integer.MAX_VALUE) {
//            System.out.println(String.format("No route found from %s to %s with exactly one intermediate step.", source, destination));
//        } else {
//            System.out.println(String.format("Minimum cost %s -> Z -> %s: %d", source, destination, minCost));
//        }
//        return minCost;
//    }

    public static void findDirectCost(String source, String destination){
        boolean validationResult = directCostValidations(source, destination);
        if(!validationResult){
            return;
        }
        Double associatedCost = directCostMap.get(source).get(destination);
        System.out.println(associatedCost);
    }

    private static boolean directCostValidations(String source, String destination){
        if(!allCitiesPool.contains(source) || !allCitiesPool.contains(destination)){
            System.out.println("either source or dest not present in all cities pool " + source + " " + destination);
            return false;
        }
        if(!directCostMap.containsKey(source)){
            System.out.println("no direct cost from source "  + source);
            return false;
        }
        if(!directCostMap.get(source).containsKey(destination)){
            System.out.println("no direct cost associated with destination in source" + destination);
            return false;
        }
        return true;
    }

    private static void parseSingleShippingDetail(String shippingDetailString){
        String[] shippingDetails = shippingDetailString.split(COMMA_DELIMITER);
        String source = shippingDetails[0].trim();
        String destination = shippingDetails[1].trim();
        String carries = shippingDetails[2].trim();
        String costString = shippingDetails[3].trim();

        Double cost = null;
        try{
            cost = Double.parseDouble(costString);
        } catch (NumberFormatException e){
            System.out.println("invalid format for cost");
            return;
        }

        directCostMap.computeIfAbsent(source, k -> new HashMap<>()).put(destination, cost);
        allCitiesPool.add(source);
        allCitiesPool.add(destination);
    }


    private static boolean validateShippingDetails(String shippingDetailsString){
        if(shippingDetailsString == null || shippingDetailsString.length() == 0){
            System.out.println("invalid shipping details" + shippingDetailsString);
            return false;
        }

        return true;
    }
}
