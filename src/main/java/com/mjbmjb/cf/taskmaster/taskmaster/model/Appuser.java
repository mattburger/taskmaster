//package com.mjbmjb.cf.taskmaster.taskmaster.model;
//
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
//
//import java.util.UUID;
//
//@DynamoDBTable(tableName = "Appuser")
//public class Appuser {
//
//    private UUID id;
//    private String username;
//    private String password;
//    private String firstname;
//    private String lastname;
//
//    public Appuser(){}
//
//    public Appuser(String username, String password, String firstname, String lastname) {
//        this.username = username;
//        this.password = password;
//        this.firstname = firstname;
//        this.lastname = lastname;
//    }
//
//    @DynamoDBHashKey
//    @DynamoDBAutoGeneratedKey
//    public UUID getId() {
//        return this.id;
//    }
//
//    @DynamoDBAttribute
//    public String getUsername() {
//        return this.username;
//    }
//
//    @DynamoDBAttribute
//    public String getPassword() {
//        return this.password;
//    }
//
//
//    @DynamoDBAttribute
//    public String getFirstname() {
//        return this.firstname;
//    }
//
//
//    @DynamoDBAttribute
//    public String getLastname() {
//        return this.lastname;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//}
