package com.zk.events;

import com.zk.experiment.Benefit;
import com.zk.experiment.Member;
import com.zk.experiment.VerificationHistory;
import com.zk.experiment.jpa.BenefitRepo;
import com.zk.experiment.jpa.VerificationHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberVerificationContext {

    @Autowired
    VerificationHistoryRepo verificationHistoryRepo;

    @Autowired
    BenefitRepo benefitRepo;


    @EventListener(classes = Member.VerificationData.class)
    public void verificate(final Member.VerificationData arg) {
        System.out.println("权益 id 为" + arg.getBenefitId());
        Optional<Benefit> byId = benefitRepo.findById(arg.getBenefitId());
        byId.ifPresent(benefit -> {
            // 校验次数
            Long aLong = verificationHistoryRepo.countAllByMember_IdAndBenefit_Id(arg.getMember().getId(),
                    arg.getBenefitId());
            if(aLong> benefit.getMaxCounts()){
                throw new RuntimeException("超出次数");
            }
            // 创建记录
            VerificationHistory verificationHistory = new VerificationHistory();
            verificationHistory.setBenefit(benefit);
            verificationHistory.setMember(arg.getMember());
            verificationHistoryRepo.save(verificationHistory);
        });
    }

}