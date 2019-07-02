package com.mjbmjb.cf.taskmaster.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.mjbmjb.cf.taskmaster.taskmaster.model.Task;
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
import java.util.Optional;

//import static org.graalvm.compiler.phases.common.DeadCodeEliminationPhase.Optionality.Optional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource( properties = {
		"amazon.dynamodb.endpoint=http://localhost:8000/",
		"amazon.aws.accesskey=test1",
		"amazon.aws.secretkey=test231"
})
public class TaskmasterApplicationTests {

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	TaskRepository taskRepository;


	@Before
	public void setup() {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest tableRequest = dynamoDBMapper
				.generateCreateTableRequest(Task.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		amazonDynamoDB.createTable(tableRequest);

		dynamoDBMapper.batchDelete(
				taskRepository.findAll());

	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void sampleCase() {
		Task newTask = new Task("Setup Time", "Setup repo on GitHub", "Available");
		taskRepository.save(newTask);

		Iterable<Task> result = taskRepository.findAll();
		Iterator<Task> iterator = result.iterator();
		List<Task> daResults = new ArrayList<>();
		while(iterator.hasNext()) {
			daResults.add(iterator.next());
		}
		assertTrue("Not empty", daResults.size() > 0);
		assertTrue("Contains item with expected cost",
				daResults.get(0).getStatus().equals("Available"));
	}

}
