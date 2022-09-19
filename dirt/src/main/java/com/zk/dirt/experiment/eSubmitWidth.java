package com.zk.dirt.experiment;

public enum eSubmitWidth {
	// 因为已经定义了带参数的构造器，所以在列出枚举值时必须传入对应的参数
	SM("sm"),
	MD("md"),
	XL("xl"),
	XS("xs"),
	LG("lg");

	// 定义一个 private 修饰的实例变量
	private String value;

	// 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
	private eSubmitWidth(String value) {
		this.value = value;
	}

	// 定义 get set 方法
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString(){
		return value;
	}

	public static void main(String[] args) {
		System.out.println(eSubmitWidth.MD);
	}
}
