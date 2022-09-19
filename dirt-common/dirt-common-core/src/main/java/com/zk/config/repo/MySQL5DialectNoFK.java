package com.zk.config.repo;

import org.hibernate.dialect.MySQL8Dialect;
// 这个类的作用是让 hibernate 在create-drop / update 模式时不生成外键
public class MySQL5DialectNoFK extends MySQL8Dialect {
	@Override
	public String getTableTypeString() {

		return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}

	//public MySQL5DialectNoFK() {
	//	super();
	//	registerFunction("order_china", new SQLFunctionTemplate(
	//			new StringType(), "convert(?1 using ?2)"));
	//
	//}
	@Override
	public String getAddForeignKeyConstraintString(
			String constraintName,
			String[] foreignKey,
			String referencedTable,
			String[] primaryKey,
			boolean referencesPrimaryKey) {
		//final String cols = String.join( ", ", foreignKey );
		//final String referencedCols = String.join( ", ", primaryKey );
		//return String.format(
		//		" add constraint %s foreign key (%s) references %s (%s)",
		//		constraintName,
		//		cols,
		//		referencedTable,
		//		referencedCols
		//);
		return "";
	}
	//@Override
	//public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
	//		String[] primaryKey, boolean referencesPrimaryKey) {
	//	//String sql = "";
	//	//for(String key : foreignKey) {
	//	//	sql += "alter table" + referencedTable + " drop foreign key " + key + ";";
	//	//}
	//	//return sql;
	//	System.out.println(constraintName);
	//	return "";
	//}
}
