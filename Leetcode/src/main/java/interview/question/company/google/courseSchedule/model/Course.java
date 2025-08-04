package interview.question.company.google.courseSchedule.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Course {
    int id;
    String name;
    List<Course> prerequisites;
}
