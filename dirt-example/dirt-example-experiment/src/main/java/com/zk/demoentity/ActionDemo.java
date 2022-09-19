package com.zk.demoentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.util.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.ApplicationContext;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ActionDemo extends DirtBaseIdEntity {



    @DirtField(title = "测试按钮",  dirtSubmit = @DirtSubmit)
    Boolean btn;


    @DirtAction(key = "toggle",text = "切换")
    public void activeAction( Boolean b){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        //applicationContext.publishEvent(new CustomSpringEvent(this, "toggle"));

        this.btn = b;
    }

    //@Transient
    //private ApplicationEventPublisher applicationEventPublisher;


}
