package com.zk.config.rest.wrapper;

public class CodeMsg {

     private int code;
	private String msg;
	
	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数绑定异常");
	public static CodeMsg DATA_ERROR = new CodeMsg(500102, "数据异常");
	public static CodeMsg   RESPONSE_PACK_ERROR = new CodeMsg(500301, "返回数据包装出错");

	public static CodeMsg ACCOUNT_NOT_EXIST_ERROR = new CodeMsg(500216, "用户不存在");
	public static CodeMsg AUTH_ERROR = new CodeMsg(500217, "auth error");
	public static CodeMsg AUTH_DENIEL_ERROR = new CodeMsg(500218, "auth deniel error");

	public static CodeMsg ENTITY_ID_NOT_EXIST_ERROR = new CodeMsg(600216, "entity id 不存在");



	public  CodeMsg(int code, String msg ) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}