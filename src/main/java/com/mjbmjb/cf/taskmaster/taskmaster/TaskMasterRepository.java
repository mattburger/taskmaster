package com.mjbmjb.cf.taskmaster.taskmaster;

import com.mjbmjb.cf.taskmaster.taskmaster.model.TaskMaster;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface TaskMasterRepository extends CrudRepository<TaskMaster, String> {

    Optional<TaskMaster> findById(UUID id);
}
