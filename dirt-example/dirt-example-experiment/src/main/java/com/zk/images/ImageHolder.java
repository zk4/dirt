package com.zk.images;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DirtEntity(value = "图片存储")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_images")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = ImageHolder.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ImageHolder extends DirtSimpleIdEntity {
	@DirtField(title = "图片",uiType = eUIType.imageUploader)
	String image;
}
