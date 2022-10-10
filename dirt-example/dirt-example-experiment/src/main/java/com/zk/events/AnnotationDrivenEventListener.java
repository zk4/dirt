package com.zk.events;

import com.zk.experiment.Benefit;
import com.zk.experiment.Member;
import com.zk.experiment.VerificationHistory;
import com.zk.experiment.jpa.BenefitRepo;
import com.zk.experiment.jpa.VerificationHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
public class AnnotationDrivenEventListener {

    // for tests
    //private boolean hitContextStartedHandler = false;
    //private boolean hitSuccessfulEventHandler = false;
    //private boolean hitCustomEventHandler = false;

    @Autowired
    VerificationHistoryRepo verificationHistoryRepo;

    @Autowired
    BenefitRepo benefitRepo;


    @EventListener(classes = String.class)
    public void handleString(final String str) {
        System.out.println("Handling context started event.String.class"+str);
        //hitContextStartedHandler = true;
    }

    @EventListener(classes = Member.VerificationData.class)
    public void verificate(final Member.VerificationData arg) {
        System.out.println("权益 id 为"+arg.getBenefitId());
        Optional<Benefit> byId = benefitRepo.findById(arg.getBenefitId());
        byId.ifPresent(benefit -> {
            VerificationHistory verificationHistory = new VerificationHistory();
            verificationHistory.setBenefit(benefit);
            verificationHistory.setMember(arg.getMember());
            verificationHistoryRepo.save(verificationHistory);
        });

        //hitContextStartedHandler = true;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleStringBeforeTransaction(final String msg) {
        System.out.println("Handling event inside a transaction BEFORE COMMIT."+msg);
        //hitCustomEventHandler = true;
    }

    @EventListener
    public void handleContextStart(final ContextStartedEvent cse) {
        System.out.println("Handling context started event.");
        //hitContextStartedHandler = true;
    }

    @EventListener(condition = "#event.success")
    public void handleSuccessful(final GenericSpringEvent<String> event) {
        System.out.println("Handling generic event (conditional): " + event.getWhat());
        //hitSuccessfulEventHandler = true;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCustom(final CustomSpringEvent event) {
        System.out.println("Handling event inside a transaction BEFORE COMMIT.");
        //hitCustomEventHandler = true;
    }

    //boolean isHitContextStartedHandler() {
    //    return hitContextStartedHandler;
    //}
    //
    //boolean isHitSuccessfulEventHandler() {
    //    return hitSuccessfulEventHandler;
    //}
    //
    //boolean isHitCustomEventHandler() {
    //    return hitCustomEventHandler;
    //}
}