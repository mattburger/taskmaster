package com.mjbmjb.cf.taskmaster.taskmaster;

import com.mjbmjb.cf.taskmaster.taskmaster.model.Appuser;
import org.springframework.data.repository.CrudRepository;

public interface AppuserRepository extends CrudRepository<Appuser, String> {
}
