package com.zk.intention.repo;

import com.zk.intention.entity.root.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
