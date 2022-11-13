package com.zk.intention.repo;

import com.zk.intention.entity.root.Intention;
import org.springframework.data.repository.CrudRepository;

public interface IntentionRepository extends CrudRepository<Intention, Long> {
}
