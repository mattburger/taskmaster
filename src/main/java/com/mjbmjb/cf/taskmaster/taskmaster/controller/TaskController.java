package com.mjbmjb.cf.taskmaster.taskmaster.controller;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.mjbmjb.cf.taskmaster.taskmaster.respository.S3Client;
import com.mjbmjb.cf.taskmaster.taskmaster.respository.TaskMasterRepository;
import com.mjbmjb.cf.taskmaster.taskmaster.model.TaskMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@RestController
public class TaskController {

    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Autowired
    TaskMasterRepository taskMasterRepository;

    String[] statusState = new String[]{"Available", "Assigned", "Accepted", "Finished"};


    @GetMapping("/")
    public @ResponseBody
    String getRootPath() {
        return "Hello, from the world of tomorrow!";
    }

    @CrossOrigin
    @GetMapping("/tasks")
    public List<TaskMaster> getTasks() {
        return (List) taskMasterRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/tasks")
    public RedirectView createTask(@RequestParam String title, String description, String assignee, String phoneNumber) {
        TaskMaster newTask = new TaskMaster(title, description, assignee, phoneNumber);
        if( !assignee.isEmpty() ) { //isBlank() doesn't want to work here
            newTask.setStatusTracker(1);
            newTask.setStatus(statusState[1]);
        } else {
            newTask.setStatusTracker(0);
            newTask.setStatus(statusState[0]);
        }
        taskMasterRepository.save(newTask);
        return new RedirectView("/tasks");
    }

    @CrossOrigin
    @GetMapping("/tasks/{id}")
    public TaskMaster getSingleTask(@RequestParam String id) {
        Optional<TaskMaster> tm = taskMasterRepository.findById(id);
        TaskMaster currentTm = tm.get();

        return  currentTm;
    }

    @CrossOrigin
    @PostMapping("/tasks/{id}")
    public RedirectView updateStatus(@RequestParam String id) {
        Optional<TaskMaster> current = taskMasterRepository.findById(id);
        TaskMaster currentTaskMaster = current.get();

        if(currentTaskMaster.getStatusTracker() < statusState.length - 1 ) {
            currentTaskMaster.setStatusTracker( currentTaskMaster.getStatusTracker() + 1 );
            currentTaskMaster.setStatus( statusState[currentTaskMaster.getStatusTracker()] );
            taskMasterRepository.save(currentTaskMaster);
        } else {
            AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

            String msg = "Task id: " + id + " is complete.";

            PublishRequest publishRequest =  new PublishRequest(System.getenv("AWS_TOPIC_ARN"), msg);
            PublishResult publishResult = snsClient.publish(publishRequest);
        }

        return new RedirectView("/tasks");

    }

    @CrossOrigin
    @DeleteMapping("/tasks/{id}")
    public RedirectView deleteTask(@RequestParam String id) {
        Optional<TaskMaster> current = taskMasterRepository.findById(id);
        TaskMaster taskToDelete = current.get();

        taskMasterRepository.delete(taskToDelete);

        return new RedirectView("/tasks");
    }

    @CrossOrigin
    @PostMapping("/tasks/{id}/images")
    public RedirectView uploadImage(
            @RequestParam String id,
            @RequestPart(value = "file") MultipartFile file
    ) {
        Optional<TaskMaster> current = taskMasterRepository.findById(id);
        TaskMaster currentTaskMaster = current.get();
        String pic = this.s3Client.uploadFile(file);

        currentTaskMaster.setPicture(pic);
        taskMasterRepository.save(currentTaskMaster);

        return new RedirectView("http://localhost:3000");
    }

    @CrossOrigin
    @GetMapping("/users/{name}/tasks")
    public List<TaskMaster> getTaskByUser(@PathVariable String name) {
        return (List) taskMasterRepository.findByAssignee(name);
    }

    @CrossOrigin
    @PostMapping("/tasks/{id}/assign/{assignee}")
    public RedirectView assignUserToTask(@RequestParam String id, @RequestParam String assignee) {
        Optional<TaskMaster> current = taskMasterRepository.findById(id);

        if(!current.isPresent()) {
            throw new IllegalStateException("Cannot change assignee of task that doesn't exist.");
        }
        TaskMaster currentTaskMaster = current.get();

        currentTaskMaster.setAssignee(assignee);
        currentTaskMaster.setStatusTracker(1);
        currentTaskMaster.setStatus(statusState[1]);

        taskMasterRepository.save(currentTaskMaster);



        return new RedirectView("/tasks");
    }

}
