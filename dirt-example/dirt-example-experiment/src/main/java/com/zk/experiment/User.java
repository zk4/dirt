package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iEnumText;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@DirtEntity("用户")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_user")
@SQLDelete(sql = "UPDATE mms_user SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class User extends DirtBaseIdEntity {




    @DirtField(title = "真实姓名"    )
    @NotEmpty
    @Size(min = 2, max = 30)
    String username;



    @DirtField(title = "手机号")
    @NotEmpty
    @Size(max = 16)
    String phoneNumber;

    enum  eGender implements  iEnumText{
        FEMALE("女性"),
        MALE("男性");

        String text;

        eGender(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }
    @DirtField(title = "性别")
    eGender gender;

    @DirtField(title = "会员生日")
    LocalDate birthday;

    @DirtField(title = "会员住址")
    String address;




    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
