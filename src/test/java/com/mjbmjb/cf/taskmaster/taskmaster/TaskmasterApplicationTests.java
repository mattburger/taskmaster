package com.mjbmjb.cf.taskmaster.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.mjbmjb.cf.taskmaster.taskmaster.model.TaskMaster;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import static org.graalvm.compiler.phases.common.DeadCodeEliminationPhase.Optionality.Optional;
import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//@ActiveProfiles("local")
