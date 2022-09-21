//package com.zk.experiment;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.zk.dirt.annotation.DirtEntity;
//import com.zk.dirt.annotation.DirtField;
//import com.zk.dirt.annotation.DirtSubmit;
//import com.zk.dirt.core.eUIType;
//import com.zk.dirt.entity.DirtBaseIdEntity;
//import com.zk.dirt.experiment.eSubmitWidth;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@DirtEntity
//@DynamicUpdate
//@DynamicInsert
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
//public class GithubRepo extends DirtBaseIdEntity {
//
//
//    @DirtField(title = "仓库名")
//    @NotNull
//    String name;
//
//    //
//    //@DirtField(
//    //        title = "标签",
//    //
//    //        uiType = eUIType.treeSelect,
//    //        enumProvider = {StatusProvider.class},
//    //        dirtSubmit = @DirtSubmit())
//    //String labels;
//
//
//
//    @DirtField(
//            title = "注释",
//            dirtSubmit =
//            @DirtSubmit(
//                    placeholder = "写注释",
//                    width = eSubmitWidth.LG)
//    )
//    String comments;
//
//
//    @DirtField(title = "创建人", dirtSubmit = @DirtSubmit(placeholder = "创建人",  width = eSubmitWidth.LG))
//    String creator;
//
//    @DirtField(
//
//            title = "仓库们",
//            uiType = eUIType.formList,
//            dirtSubmit = @DirtSubmit(valueType = eUIType.formList)
//    )
//    @OneToMany(targetEntity =  GithubBug.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//
//    List< GithubBug> githubBugList;
//
//
//}
