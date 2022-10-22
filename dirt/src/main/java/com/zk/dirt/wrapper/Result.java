package com.zk.dirt.wrapper;


import org.springframework.data.domain.Page;

import java.util.List;

public class Result<T> {

	private int code;
	private String msg;
	private T data;
	private PageResult page;
	/**
	 *  成功时候的调用
	 * */
	public static  <T> Result<T> success(T data){
		return new Result<T>(data);
	}
	public static  <T> Result ok() {
		Result result = new Result();
		result.setCode(0);
		result.setData("");
		return result;
	}
		public static  <T> Result success(Page<T> data){
		Result<List<T>> success = Result.success(data.getContent());
		PageResult pageResult = new PageResult();
		pageResult.setCurPage(data.getPageable().getPageNumber());
		pageResult.setPageSize(data.getSize());
		pageResult.setTotalPages(data.getTotalPages());
		success.setPage(pageResult);

		return success;
	}

	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(CodeMsg codeMsg,T data){
		return new Result<T>(data,codeMsg);
	}
	public  Result(){}
	private Result(T data) {
		this.data = data;
	}
	private Result(T data,CodeMsg codeMsg) {
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
		this.data = data;
	}
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public PageResult getPage() {
		return page;
	}

	public Result<T> setPage(PageResult page) {
		this.page = page;
		return this;
	}

	@Override
	public String toString() {
		String message =  "Result{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
		System.out.println(message);
		return message;
	}
}
