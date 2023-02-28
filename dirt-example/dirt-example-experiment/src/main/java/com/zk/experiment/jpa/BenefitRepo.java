package com.zk.experiment.jpa;

import com.zk.experiment.entity.Benefit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

// 自定义 interface
public interface BenefitRepo extends PagingAndSortingRepository<Benefit, Long>, JpaSpecificationExecutor<Benefit> {

}
