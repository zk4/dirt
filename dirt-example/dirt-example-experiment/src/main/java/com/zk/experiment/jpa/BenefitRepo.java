package com.zk.experiment.jpa;

import com.zk.experiment.Benefit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BenefitRepo extends PagingAndSortingRepository<Benefit, Long>, JpaSpecificationExecutor<Benefit> {

}
