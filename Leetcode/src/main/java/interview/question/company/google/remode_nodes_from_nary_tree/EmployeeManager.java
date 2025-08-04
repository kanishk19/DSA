package interview.question.company.google.remode_nodes_from_nary_tree;

import java.util.*;

public class EmployeeManager {
    private Employee root;

    public EmployeeManager(){
        this.root = null;
    }

    private Employee buildEmployee(int employeeId, boolean isEngineer, List<Employee> directReports){
        return Employee.builder()
                .employeeId(employeeId)
                .isEngineer(isEngineer)
                .directReports(directReports)
                .build();
    }

    public void printEmployeeHierarchy(Employee root){
        if(root == null)
            return;

        Deque<Employee> employeeQueue = new ArrayDeque<>();
        employeeQueue.add(root);

        while(!employeeQueue.isEmpty()){
            int currLevelSize = employeeQueue.size();
            while(currLevelSize > 0){
                Employee currEmployee = employeeQueue.poll();
                System.out.print(currEmployee + " ");

                for(Employee report : currEmployee.getDirectReports()){
                    employeeQueue.add(report);
                }
                currLevelSize -= 1;
            }
            System.out.println();
        }
    }

    public Employee buildEmployeeHierarchy(){
        Employee employee9 = buildEmployee(9, false, Collections.EMPTY_LIST);
        Employee employee8 = buildEmployee(8, true, Collections.EMPTY_LIST);
        List<Employee> directReports7 = Arrays.asList(employee8, employee9);
        Employee employee7 = buildEmployee(7, false, directReports7);

        Employee employee5 = buildEmployee(5, false, Collections.EMPTY_LIST);
        Employee employee6 = buildEmployee(6, true, Collections.EMPTY_LIST);
        List<Employee> directReports2 = Arrays.asList(employee5, employee6);
        Employee employee2 = buildEmployee(2, true, directReports2);

        List<Employee> directReport3 = Arrays.asList(employee7);
        Employee employee3 = buildEmployee(3, true, directReport3);
        Employee employee4 = buildEmployee(4, true, Collections.EMPTY_LIST);

        List<Employee> directReports1 = Arrays.asList(employee2, employee3, employee4);
        Employee employee1 = buildEmployee(1, true, directReports1);

        this.root = employee1;
        return this.root;
    }

    public Employee removeNonEngineers(){
        return removeNonEngineersInplace();
    }

    private Employee removeNonEngineersInplace(){
        if(root == null || root.getDirectReports() == null || root.getDirectReports().size() == 0)
            return this.root;

        return this.root;
    }

//    private

    private Employee removeNonEngineersNewGraph(){
        Employee newRoot = this.root;
        return newRoot;
    }
}
