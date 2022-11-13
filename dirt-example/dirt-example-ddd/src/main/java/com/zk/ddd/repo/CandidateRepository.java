package com.zk.ddd.repo;

import com.zk.ddd.entity.root.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
