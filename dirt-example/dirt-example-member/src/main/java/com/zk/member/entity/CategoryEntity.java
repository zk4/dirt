package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class CategoryEntity extends DirtBaseIdEntity {
	/**
	 * 分类名称
	 */
	@DirtField(title = "分类名称",  dirtSubmit = @DirtSubmit )
	@Size(min = 2, max = 30)
	private String name;
	//
	//@DirtField(idOfEntity = CategoryEntity.class)
	//
	//@ManyToOne(fetch = FetchType.LAZY)
	//CategoryEntity parent;
	//
	//@DirtField(title = "parent id",
	//		idOfEntity = CategoryEntity.class,
	//
	//		filters = true,
	//		onFilter = true,
	//		dirtSubmit = @DirtSubmit
	//)
	//@Column(name = "parentId")
	//Long parentId;
	/*
	 * 层级
	 */
	private Integer catLevel;

	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标地址
	 */
	private String icon;
	/**
	 * 计量单位
	 */
	private String productUnit;
	/**
	 * 商品数量
	 */
	private Integer productCount;


	@DirtField(title = "子菜单",idOfEntity = CategoryEntity.class)
	@OneToMany(cascade = CascadeType.MERGE)
	private List<CategoryEntity> subMenus;

	@DirtAction(text = "详情", key = "detail")
	public void detail() {}

	@DirtAction(text = "删除", key = "delete")
	public void delete() {}

	@DirtAction(text = "编辑", key = "edit")
	public void edit() {}

}