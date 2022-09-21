//package com.zk.experiment;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.zk.experiment.provider.StatusProvider;
//import com.zk.experiment.types.eWeekDay;
//import com.zk.dirt.annotation.DirtEntity;
//import com.zk.dirt.annotation.DirtField;
//import com.zk.dirt.annotation.DirtSubmit;
//import com.zk.dirt.core.eUIType;
//import com.zk.dirt.entity.DirtBaseIdEntity;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.Entity;
//
//@Getter
//@Setter
//@Entity
//@DirtEntity
//@DynamicUpdate
//@DynamicInsert
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
//public class EnumDemo extends DirtBaseIdEntity {
//
//
//    @DirtField(
//            title = "单标签",
//
//            filters = true,
//            onFilter = true,
//            uiType = eUIType.treeSelect,
//            enumProvider = {StatusProvider.class},
//            dirtSubmit = @DirtSubmit())
//    String singleStatus;
//
//    @DirtField(
//            title = "星期",
//            uiType = eUIType.select,
//            enumListableType= eWeekDay.class,
//            dirtSubmit = @DirtSubmit())
//    eWeekDay currentday;
//}
