package com.example.finalproject.java.Database;

public class AssignmentApi {
    private String courseId;
    private String deadline;
    private String point;
    private String name;
    private String fileName;

    public AssignmentApi(String courseId, String deadline, String point, String name, String fileName) {
        this.courseId = courseId;
        this.deadline = deadline;
        this.point = point;
        this.name = name;
        this.fileName = fileName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
