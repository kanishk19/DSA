package interview.question.company.google.remode_nodes_from_nary_tree;

public class Application {
    static EmployeeManager employeeManager = new EmployeeManager();

    public static void main(String[] args){
        Employee root = employeeManager.buildEmployeeHierarchy();
        employeeManager.printEmployeeHierarchy(root);
    }
}
