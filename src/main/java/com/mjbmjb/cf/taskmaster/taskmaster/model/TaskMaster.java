package com.mjbmjb.cf.taskmaster.taskmaster.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "taskmaster")
public class TaskMaster {

    private String id;
    private String title;
    private String description;
    private String status;
    private String assignee;
    private int statusTracker;
    private String picture;
    private String phoneNumber;

    public TaskMaster(){}

    public TaskMaster(String title, String description, String assignee, String phoneNumber){
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.phoneNumber = phoneNumber;
    }

    //getters
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return this.id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return this.title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return this.description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return this.status;
    }

    @DynamoDBAttribute
    public int getStatusTracker() {
        return this.statusTracker;
    }

    @DynamoDBAttribute
    public String getAssignee() {
        return this.assignee;
    }

    @DynamoDBAttribute
    public String getPicture() {
        return this.picture;
    }

    @DynamoDBAttribute
    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusTracker(int statusTracker) {
        this.statusTracker = statusTracker;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
