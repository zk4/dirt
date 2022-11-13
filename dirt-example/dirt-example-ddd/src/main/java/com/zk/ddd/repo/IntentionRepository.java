package com.zk.ddd.repo;

import com.zk.ddd.entity.root.Intention;
import org.springframework.data.repository.CrudRepository;

public interface IntentionRepository extends CrudRepository<Intention, Long> {
}
