package com.mjbmjb.cf.taskmaster.taskmaster.model;

public class Task {

    private String title;
    private String description;
    private String status;
    private String[] statusState = new String[]{"Available", "Assigned", "Accepted", "Finished"};

    public Task(){}

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
