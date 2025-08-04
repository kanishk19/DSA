package interview.question.company.google.courseSchedule.graph;

import interview.question.company.google.courseSchedule.model.Course;

public interface ICourseGraph {
    public void addCourse(Course course);
    public void addPrerequisite(Course course, Course prerequisite);
    public void getIndegree(Course course);
}
