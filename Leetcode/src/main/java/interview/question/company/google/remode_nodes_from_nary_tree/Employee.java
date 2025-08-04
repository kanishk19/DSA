package interview.question.company.google.remode_nodes_from_nary_tree;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Employee {
    private final int employeeId;
    private final boolean isEngineer;
    private final List<Employee> directReports;

    @Override
    public String toString(){
        return "Employee : {" +
                " employeeId : " + employeeId +
                " isEngineer : " + isEngineer + " }";
//                " directReports : " + directReports;
    }
}
