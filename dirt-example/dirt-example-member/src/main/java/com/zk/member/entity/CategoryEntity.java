package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSubmit;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.experiment.eSubmitWidth;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
public class CategoryEntity extends BaseIdEntity2 {
	/**
	 * 分类名称
	 */
	@DirtField(title = "分类名称",  dirtSubmit = @DirtSubmit )
	@NotEmpty
	@Size(min = 2, max = 30)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", insertable = false, updatable = false)
	@JsonIgnore
	CategoryEntity parent;

	@DirtField(title = "parent id",
			idOfEntity = CategoryEntity.class,

			filters = true,
			onFilter = true,
			dirtSubmit = @DirtSubmit
	)
	@Column(name = "parentId")
	Long parentId;
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


	@DirtField(
			title = "子菜单",
			width = eSubmitWidth.LG,
			uiType = eUIType.formList,
			dirtSubmit = @DirtSubmit(width = eSubmitWidth.LG)
	)
	@OneToMany(mappedBy = "parent",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CategoryEntity> subMenus;

	@DirtAction(text = "详情", key = "detail")
	public void detail() {}

	@DirtAction(text = "删除", key = "delete")
	public void delete() {}

	@DirtAction(text = "编辑", key = "edit")
	public void edit() {}

}