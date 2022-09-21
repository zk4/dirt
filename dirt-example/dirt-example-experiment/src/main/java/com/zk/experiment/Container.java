//package com.zk.experiment;
//
//import com.zk.experiment.provider.StatusProvider;
//import com.zk.dirt.annotation.DirtField;
//import com.zk.dirt.core.eUIType;
//import com.zk.dirt.entity.DirtBaseIdEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//public class Container extends DirtBaseIdEntity {
//
//    @DirtField(title = "应用名称",copyable = true)
//    String name;
//
//    @DirtField(title = "状态",enumProvider ={StatusProvider.class} )
//    String status;
//
//    @DirtField(title = "容器数")
//    String containers;
//
//    @DirtField(title = "创建者",search = true)
//    String creator;
//
//
//    @DirtField(title = "备注",copyable = true,search = true)
//    String memo;
//
//    @DirtField(title = "备注2",copyable = true,search = true)
//    String remark;
//
//
//    @DirtField(title = "时间",copyable = true,search = true)
//    LocalDateTime mytime;
//
//    @DirtField(title = "开关",copyable = true, uiType = eUIType.switching)
//    Boolean  flicker;
//}
