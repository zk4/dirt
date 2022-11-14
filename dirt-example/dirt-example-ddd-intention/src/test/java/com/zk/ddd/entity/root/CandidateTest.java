package com.zk.ddd.entity.root;

import com.zk.IntentionApplication;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.intention.entity.root.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest(classes = IntentionApplication.class)
@Rollback(value = false)
class CandidateTest {

    @Autowired
    iPersistProxy persistProxy;

    @Autowired
    EntityManager entityManager;
    private SimpleJpaRepository simpleJpaRepository;

    @BeforeEach
    void setUp() {
        simpleJpaRepository = new SimpleJpaRepository(Candidate.class,
                entityManager);
    }

    @Test
    @Transactional
    void saveCandidate() {
        Candidate candidate = new Candidate();
        //candidate.setLocation(new Location(1.2,2.2));
        Object save = persistProxy.save(Candidate.class, candidate);
        System.out.println(save);
    }
}