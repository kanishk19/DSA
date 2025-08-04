package interview.question.company.google.remode_nodes_from_nary_tree;

import java.util.*;


/**
 * Converts a full organizational graph into an Engineer-only graph.
 * The root of the original organization is always an Engineer.
 */
class EngineerGraphConverter {

    /**
     * Represents an Employee in an organizational structure.
     */
    static class Employee {
        int employeeId;
        boolean isEngineer;
        List<Employee> reportees; // Direct reports of this employee

        public Employee(int employeeId, boolean isEngineer) {
            this.employeeId = employeeId;
            this.isEngineer = isEngineer;
            this.reportees = new ArrayList<>();
        }

        public void addReportee(Employee reportee) {
            this.reportees.add(reportee);
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public boolean isEngineer() {
            return isEngineer;
        }

        public List<Employee> getReportees() {
            return reportees;
        }

        @Override
        public String toString() {
            return "E" + employeeId + (isEngineer ? "(Eng)" : "(Non-Eng)");
        }
    }

    /**
     * Converts a given organizational graph (represented by its root employee)
     * into a new graph containing only Engineers.
     * The hierarchy is maintained such that an Engineer directly reports to
     * the nearest Engineer above them in the original hierarchy.
     *
     * @param root The root employee of the original organizational structure.
     * @return The root of the new Engineer-only graph. Returns null if the
     * original root is not an Engineer or if no Engineers are found.
     */
    public Employee convertToEngineerOnlyGraph(Employee root) {
        if (root == null || !root.isEngineer()) {
            System.out.println("Original root is null or not an Engineer. Cannot form Engineer-only graph.");
            return null; // The problem states root is always Engineer, but good for robustness.
        }

        // Map to store the new Engineer-only graph nodes, keyed by original employee ID
        Map<Integer, Employee> engineerNodes = new HashMap<>();

        // Perform a BFS traversal to find all Engineers and establish their new reporting lines.
        // We need to keep track of the closest engineer manager for each employee encountered.
        Queue<Employee> queue = new LinkedList<>();
        queue.offer(root);

        // This map stores the new "parent" (closest engineer manager) for each engineer node
        // in the engineer-only graph.
        Map<Employee, Employee> newEngineerParents = new HashMap<>();

        // The new root of the engineer-only graph
        Employee newRoot = new Employee(root.getEmployeeId(), true);
        engineerNodes.put(root.getEmployeeId(), newRoot);

        // Set to track visited employees in the original graph to prevent cycles in traversal
        Set<Employee> visitedOriginal = new HashSet<>();
        visitedOriginal.add(root);

        while (!queue.isEmpty()) {
            Employee currentEmployee = queue.poll();

            // Iterate through reportees to find the next engineer in the hierarchy
            for (Employee reportee : currentEmployee.getReportees()) {
                if (visitedOriginal.contains(reportee)) {
                    continue; // Skip if already processed
                }
                visitedOriginal.add(reportee);

                if (reportee.isEngineer()) {
                    // If the reportee is an Engineer, add them to the engineer-only graph
                    // and establish a direct reporting line from the current engineer.
                    Employee newReporteeNode = engineerNodes.computeIfAbsent(
                            reportee.getEmployeeId(), id -> new Employee(id, true));

                    // The currentEmployee (which is an engineer) is the new manager for newReporteeNode
                    engineerNodes.get(currentEmployee.getEmployeeId()).addReportee(newReporteeNode);
                    newEngineerParents.put(newReporteeNode, engineerNodes.get(currentEmployee.getEmployeeId()));

                    // Continue BFS from this engineer reportee
                    queue.offer(reportee);
                } else {
                    // If the reportee is a Non-Engineer, they don't appear in the new graph directly.
                    // However, their reportees might be engineers. We need to "skip" this non-engineer
                    // and connect their reportees to the current engineer.
                    // This means the current engineer's "influence" extends through this non-engineer.
                    // So, add the non-engineer's reportees to the queue, but their new parent will be
                    // the currentEngineer.
                    // To do this correctly, we need to pass down the "closest engineer ancestor".
                    // Let's re-think the BFS to track the 'closestEngineerAncestor' for each node.

                    // Simpler approach: When we find a non-engineer, we effectively make their direct
                    // reportees report to the *current engineer* in the new graph.
                    // This is handled by the BFS structure: any reportee of 'currentEmployee'
                    // (which is an engineer in the new graph) will try to connect.

                    // For non-engineers, we still need to explore their reportees to find engineers.
                    // The 'currentEmployee' is the closest engineer ancestor for 'reportee'.
                    // So, when processing 'reportee's children, their new parent will be 'currentEmployee'.

                    // We need a way to track the closest engineer ancestor.
                    // Let's use a custom BFS queue element: Pair<Employee, Employee> (current, closestEngineerAncestor)
                    // Or, more simply, when we encounter a non-engineer, just add their reportees to the queue
                    // and the *currentEmployee* (who is an engineer) remains the candidate parent for *their* engineer reportees.

                    // The BFS logic needs to ensure that if a non-engineer is encountered, their children
                    // are processed, and if those children are engineers, they report to the *closest engineer ancestor*.
                    // The current BFS naturally does this: 'currentEmployee' is always an engineer (or the root).
                    // So, if 'reportee' is non-engineer, its children are added to the queue, and when those children
                    // are processed, their 'closest engineer ancestor' will be 'currentEmployee'.

                    // This means, if reportee is non-engineer, we just add their reportees to the queue
                    // and the new 'parent' for those reportees (if they are engineers) will be 'currentEmployee'.
                    queue.offer(reportee); // Continue exploring down the hierarchy
                }
            }
        }

        // The above BFS logic for building the new graph is slightly flawed for the "closest engineer" rule.
        // Let's refine the BFS to explicitly pass down the 'closestEngineerAncestor'.

        // Reset for a more precise BFS
        engineerNodes.clear();
        visitedOriginal.clear();

        // Queue stores pairs: [current employee in original graph, its closest engineer ancestor in new graph]
        // For the root, its closest engineer ancestor is itself (the new root)
        Queue<Map.Entry<Employee, Employee>> bfsQueue = new LinkedList<>();

        Employee newRootNode = new Employee(root.getEmployeeId(), true);
        engineerNodes.put(root.getEmployeeId(), newRootNode); // Add root to engineer-only graph
        bfsQueue.offer(new AbstractMap.SimpleEntry<>(root, newRootNode));
        visitedOriginal.add(root);

        while (!bfsQueue.isEmpty()) {
            Map.Entry<Employee, Employee> entry = bfsQueue.poll();
            Employee currentOriginalEmployee = entry.getKey();
            Employee closestEngineerAncestor = entry.getValue(); // This is a node in the *new* graph

            for (Employee reportee : currentOriginalEmployee.getReportees()) {
                if (visitedOriginal.contains(reportee)) {
                    continue;
                }
                visitedOriginal.add(reportee);

                if (reportee.isEngineer()) {
                    // If reportee is an engineer, create its node in the new graph (if not exists)
                    // and link it to the closestEngineerAncestor.
                    Employee newReporteeNode = engineerNodes.computeIfAbsent(
                            reportee.getEmployeeId(), id -> new Employee(id, true));

                    closestEngineerAncestor.addReportee(newReporteeNode);

                    // This newReporteeNode is now the closest engineer ancestor for its own reportees
                    bfsQueue.offer(new AbstractMap.SimpleEntry<>(reportee, newReporteeNode));
                } else {
                    // If reportee is a non-engineer, it's skipped in the new graph.
                    // Its reportees will still report to the *same* closestEngineerAncestor.
                    bfsQueue.offer(new AbstractMap.SimpleEntry<>(reportee, closestEngineerAncestor));
                }
            }
        }

        return newRootNode;
    }

    /**
     * Helper method to print the Engineer-only graph (for verification).
     * Uses BFS to print level by level.
     */
    public void printEngineerGraph(Employee root) {
        if (root == null) {
            System.out.println("Engineer-only graph is empty.");
            return;
        }

        System.out.println("\nEngineer-Only Graph Structure:");
        Queue<Employee> queue = new LinkedList<>();
        queue.offer(root);
        Set<Employee> visitedPrint = new HashSet<>(); // To handle potential cycles or re-visits in print
        visitedPrint.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Employee current = queue.poll();
                System.out.print(current.getEmployeeId() + " -> [");
                for (int j = 0; j < current.getReportees().size(); j++) {
                    Employee reportee = current.getReportees().get(j);
                    System.out.print(reportee.getEmployeeId());
                    if (j < current.getReportees().size() - 1) {
                        System.out.print(", ");
                    }
                    if (!visitedPrint.contains(reportee)) {
                        queue.offer(reportee);
                        visitedPrint.add(reportee);
                    }
                }
                System.out.print("]   ");
            }
            System.out.println(); // New line for each level
        }
    }

    public static void main(String[] args) {
        EngineerGraphConverter converter = new EngineerGraphConverter();

        // Construct the example organizational tree:
        //                      E1 (Eng)
        //          ______________|______________
        //         |              |              |
        //         E2 (Eng)      NE1 (Non-Eng)   E3 (Eng)
        //      ___|___             |
        //      |     |             |
        //      E4 (Eng) NE2 (Non-Eng) E5 (Eng)

        Employee e1 = new Employee(1, true);
        Employee e2 = new Employee(2, true);
        Employee ne1 = new Employee(101, false); // Non-Engineer
        Employee e3 = new Employee(3, true);
        Employee e4 = new Employee(4, true);
        Employee ne2 = new Employee(102, false); // Non-Engineer
        Employee e5 = new Employee(5, true);

        e1.addReportee(e2);
        e1.addReportee(ne1);
        e1.addReportee(e3);

        e2.addReportee(e4);
        e2.addReportee(ne2);

        ne1.addReportee(e5); // NE1 manages E5

        System.out.println("Original Graph Setup:");
        System.out.println("E1 reports to: " + e1.getReportees());
        System.out.println("E2 reports to: " + e2.getReportees());
        System.out.println("NE1 reports to: " + ne1.getReportees());


        // Convert to Engineer-only graph
        Employee engineerRoot = converter.convertToEngineerOnlyGraph(e1);

        // Print the resulting Engineer-only graph
        // Expected:
        // E1 -> [E2, E5, E3]
        // E2 -> [E4]
        // E3 -> []
        // E4 -> []
        // E5 -> []
        converter.printEngineerGraph(engineerRoot);

        System.out.println("\n--- Test Case 2: No Non-Engineers ---");
        Employee e1_no_ne = new Employee(1, true);
        Employee e2_no_ne = new Employee(2, true);
        Employee e3_no_ne = new Employee(3, true);
        e1_no_ne.addReportee(e2_no_ne);
        e2_no_ne.addReportee(e3_no_ne);
        Employee engineerRoot2 = converter.convertToEngineerOnlyGraph(e1_no_ne);
        converter.printEngineerGraph(engineerRoot2);
        // Expected: E1 -> [E2], E2 -> [E3]

        System.out.println("\n--- Test Case 3: Engineer reports to Non-Engineer ---");
        Employee r1 = new Employee(1, true);
        Employee r2 = new Employee(2, false); // Non-Engineer
        Employee r3 = new Employee(3, true);
        r1.addReportee(r2);
        r2.addReportee(r3); // Engineer reports to Non-Engineer
        Employee engineerRoot3 = converter.convertToEngineerOnlyGraph(r1);
        converter.printEngineerGraph(engineerRoot3);
        // Expected: R1 -> [R3]

        System.out.println("\n--- Test Case 4: Disconnected Engineers (should still connect to closest ancestor) ---");
        Employee d1 = new Employee(1, true);
        Employee d2 = new Employee(2, false);
        Employee d3 = new Employee(3, false);
        Employee d4 = new Employee(4, true);
        d1.addReportee(d2);
        d2.addReportee(d3);
        d3.addReportee(d4);
        Employee engineerRoot4 = converter.convertToEngineerOnlyGraph(d1);
        converter.printEngineerGraph(engineerRoot4);
        // Expected: D1 -> [D4]
    }
}

