package com.mjbmjb.cf.taskmaster.taskmaster.controller;

import com.mjbmjb.cf.taskmaster.taskmaster.TaskMasterRepository;
import com.mjbmjb.cf.taskmaster.taskmaster.model.TaskMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@RestController
public class TaskController {

    @Autowired
    TaskMasterRepository taskMasterRepository;

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
    public RedirectView createTask(@RequestParam String title, String description) {
        TaskMaster newTask = new TaskMaster(title, description);
        taskMasterRepository.save(newTask);
        return new RedirectView("/tasks");
    }

    @PostMapping("/tasks/{id}")
    public RedirectView updateStatus(@RequestParam String id) {
        String[] statusState = new String[]{"Available", "Assigned", "Accepted", "Finished"};
        Optional<TaskMaster> current = taskMasterRepository.findById(id);
        TaskMaster currentTaskMaster = current.get();

        if(currentTaskMaster.getStatusTracker() != statusState.length) {
            currentTaskMaster.setStatusTracker(currentTaskMaster.getStatusTracker() + 1);
            currentTaskMaster.setStatus(statusState[currentTaskMaster.getStatusTracker()]);
            taskMasterRepository.save(currentTaskMaster);
        }

        return new RedirectView("/tasks");

    }
// for future form use
//    @GetMapping("/")
//    public String getRootPath() {
//        return "helloWorld";
//    }

//    @GetMapping("/tasks")
//    public String getTasks(Model m) {
//        Iterable<TaskMaster> tasks = taskMasterRepository.findAll();
//        m.addAttribute("tasks", tasks);
//        return "viewTasks";
//    }
//
//    @PostMapping("/tasks")
//    public RedirectView createTasks(String title, String description) {
//        TaskMaster newTaskMaster = new TaskMaster(title, description);
//        taskMasterRepository.save(newTaskMaster);
//
//        return new RedirectView("/tasks");
//    }

//    @GetMapping("/tasks/{id}")
//    public String viewSpecificTask(@PathVariable String id, Model m) {
//
//        Optional<TaskMaster> current = taskMasterRepository.findById(id);
//        TaskMaster taskMasterToUpdate = current.get();
//        m.addAttribute("task", taskMasterToUpdate);
//        return "updateTaskStatus";
//    }

//    @PostMapping("/tasks/{id}")
//    public RedirectView updateSpecificTask(@PathVariable String id, Model m) {
//        Optional<TaskMaster> current = taskMasterRepository.findById(id);
//        TaskMaster currentTaskMaster = current.get();
//        if(currentTaskMaster.getStatusTracker() != currentTaskMaster.getStatusState().length) {
//            currentTaskMaster.setStatusTracker(currentTaskMaster.getStatusTracker() + 1);
//            currentTaskMaster.setStatus(currentTaskMaster.getStatusState()[currentTaskMaster.getStatusTracker()]);
//            taskMasterRepository.save(currentTaskMaster);
//        }
//
//        return new RedirectView("/tasks");
//    }
}
