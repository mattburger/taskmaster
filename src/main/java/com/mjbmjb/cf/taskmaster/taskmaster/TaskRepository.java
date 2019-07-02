package com.mjbmjb.cf.taskmaster.taskmaster;

import com.mjbmjb.cf.taskmaster.taskmaster.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
}
