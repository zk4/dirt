package com.zk.experiment.jpa;

import com.zk.experiment.VerificationHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VerificationHistoryRepo  extends PagingAndSortingRepository<VerificationHistory, Long>, JpaSpecificationExecutor<VerificationHistory> {
    Long countAllByMember_IdAndBenefit_Id(Long memberId, Long benefitId );
}
