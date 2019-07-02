package com.mjbmjb.cf.taskmaster.taskmaster.controller;

import com.mjbmjb.cf.taskmaster.taskmaster.TaskRepository;
import com.mjbmjb.cf.taskmaster.taskmaster.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String getRootPath() {
        return "helloWorld";
    }

    @GetMapping("/tasks")
    public String getTasks(Model m) {
        Iterable<Task> tasks = taskRepository.findAll();
        m.addAttribute("tasks", tasks);
        return "viewTasks";
    }

    @PostMapping("/tasks")
    public RedirectView createTasks(String title, String description) {
        Task newTask = new Task(title, description);
        taskRepository.save(newTask);

        return new RedirectView("/tasks");
    }

    @GetMapping("/tasks/{id}")
    public String viewSpecificTask(@PathVariable UUID id, Model m) {

        Optional<Task> current = taskRepository.findById(id);
        Task taskToUpdate = current.get();
        m.addAttribute("task", taskToUpdate);
        return "updateTaskStatus";
    }

    @PostMapping("/tasks/{id}")
    public RedirectView updateSpecificTask(@PathVariable UUID id, Model m) {
        Optional<Task> current = taskRepository.findById(id);
        Task currentTask = current.get();
        if(currentTask.getStatusTracker() != currentTask.getStatusState().length) {
            currentTask.setStatusTracker(currentTask.getStatusTracker() + 1);
            currentTask.setStatus(currentTask.getStatusState()[currentTask.getStatusTracker()]);
        }

        return new RedirectView("/tasks");
    }
}
