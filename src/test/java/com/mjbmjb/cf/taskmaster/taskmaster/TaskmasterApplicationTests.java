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
	TaskMasterRepository taskMasterRepository;


	@Before
	public void setup() {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest tableRequest = dynamoDBMapper
				.generateCreateTableRequest(TaskMaster.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		amazonDynamoDB.createTable(tableRequest);

		dynamoDBMapper.batchDelete(
				taskMasterRepository.findAll());

	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void sampleCase() {
		TaskMaster newTaskMaster = new TaskMaster("Setup Time", "Setup repo on GitHub");
		taskMasterRepository.save(newTaskMaster);

		Iterable<TaskMaster> result = taskMasterRepository.findAll();
		Iterator<TaskMaster> iterator = result.iterator();
		List<TaskMaster> daResults = new ArrayList<>();
		while(iterator.hasNext()) {
			daResults.add(iterator.next());
		}
		assertTrue("Not empty", daResults.size() > 0);
		assertTrue("Contains item with expected cost",
				daResults.get(0).getStatus().equals("Available"));
	}

}
