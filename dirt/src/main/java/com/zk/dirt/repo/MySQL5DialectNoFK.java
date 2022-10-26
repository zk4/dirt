package com.zk.dirt.repo;

import org.hibernate.dialect.MySQL8Dialect;

// 这个类的作用是让 hibernate 在create-drop / update 模式时不生成外键
// 使用方式，在　yml　配置文件里加上
/*
  jpa:
    properties:
      hibernate:
        format_sql: true
        dialect: com.zk.dirt.repo.MySQL5DialectNoFK
 */
public class MySQL5DialectNoFK extends MySQL8Dialect {
	@Override
	public String getTableTypeString() {

		return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}


	@Override
	public String getAddForeignKeyConstraintString(
			String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {

		return "";
	}

}
