//package com.zk.experiment;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.zk.dirt.annotation.DirtEntity;
//import com.zk.dirt.annotation.DirtField;
//import com.zk.dirt.annotation.DirtSubmit;
//import com.zk.dirt.entity.DirtBaseIdEntity;
//import com.zk.dirt.experiment.eSubmitWidth;
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
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
//public class Card extends DirtBaseIdEntity {
//
//
//    @DirtField(title = "卡名")
//    String name;
//
//
//    @DirtField(title = "卡名haha")
//    String namehaha;
//    //
//    //@DirtField(
//    //        title = "状态",
//    //
//    //        uiType = eUIType.treeSelect,
//    //        dirtSubmit = @DirtSubmit())
//    //eStatus status;
//    //
//    //
//    //
//    //@DirtField(title = "卡类型",uiType = eUIType.select)
//    //@Enumerated(value = EnumType.STRING)
//    //eCardType cardtype;
//
//    @DirtField(title = "创建人", dirtSubmit = @DirtSubmit(placeholder = "创建人",  width = eSubmitWidth.LG))
//    String creator;
//
//    //@OneToMany(mappedBy = "githubRepo")
//    //List<GithubBug> githubBugList;
//
//
//
//}
