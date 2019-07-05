package com.mjbmjb.cf.taskmaster.taskmaster.controller;

import com.mjbmjb.cf.taskmaster.taskmaster.TaskMasterRepository;
import com.mjbmjb.cf.taskmaster.taskmaster.model.TaskMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@RestController
public class TaskController {

    @Autowired
    TaskMasterRepository taskMasterRepository;

    String[] statusState = new String[]{"Available", "Assigned", "Accepted", "Finished"};


    @GetMapping("/")
    public @ResponseBody
    String getRootPath() {
        return "Hello, from the world of tomorrow!";
    }

    @GetMapping("/tasks")
    public List<TaskMaster> getTasks() {
        return (List) taskMasterRepository.findAll();
    }

    @PostMapping("/tasks")
    public RedirectView createTask(@RequestParam String title, String description, String assignee) {
        TaskMaster newTask = new TaskMaster(title, description, assignee);
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

    @PostMapping("/tasks/{id}")
    public RedirectView updateStatus(@RequestParam String id) {
        Optional<TaskMaster> current = taskMasterRepository.findById(id);
        TaskMaster currentTaskMaster = current.get();

        if(currentTaskMaster.getStatusTracker() != statusState.length ) {
            currentTaskMaster.setStatusTracker( currentTaskMaster.getStatusTracker() + 1 );
            currentTaskMaster.setStatus( statusState[currentTaskMaster.getStatusTracker()] );
            taskMasterRepository.save(currentTaskMaster);
        }

        return new RedirectView("/tasks");

    }

    @GetMapping("/users/{name}/tasks")
    public List<TaskMaster> getTaskByUser(@PathVariable String name) {
        return (List) taskMasterRepository.findByAssignee(name);
    }

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
