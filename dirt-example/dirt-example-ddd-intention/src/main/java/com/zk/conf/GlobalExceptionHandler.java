package com.zk.conf;


import com.zk.conf.wrapper.CodeMsg;
import com.zk.conf.wrapper.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler   {


	@ExceptionHandler(value= Exception.class)
	public Result exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException ex = (GlobalException)e;
			return Result.error(ex.getCm());
		}else if(e instanceof BindException) {
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR,msg);
		}
		// 一数据库唯一冲突时
		else if (e instanceof DataIntegrityViolationException){
			DataIntegrityViolationException ex = (DataIntegrityViolationException)e;
			return Result.error(CodeMsg.DATA_ERROR,ex.getRootCause().getLocalizedMessage());
		}
		else if(e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
			List<Map<String, String>> list = new ArrayList<>();
			for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
				Map<String, String> map = new HashMap<>();
				if (objectError instanceof FieldError) {
					FieldError fieldError = (FieldError) objectError;
					map.put("field", fieldError.getField());
					map.put("message", fieldError.getDefaultMessage());
				} else {
					map.put("field", objectError.getObjectName());
					map.put("message", objectError.getDefaultMessage());
				}
				list.add(map);
			}
			return Result.error(CodeMsg.BIND_ERROR,list);
		}
		// 得到在事务异常里的包裹的 RuntimeException 信息
		else if (e instanceof TransactionSystemException){
			TransactionSystemException ex = (TransactionSystemException)e;
			return Result.error(CodeMsg.SERVER_ERROR, Arrays.asList(ex.getRootCause().getLocalizedMessage()));

		}
		else if (e instanceof InvocationTargetException){
			InvocationTargetException ex = (InvocationTargetException)e;

			return Result.error(CodeMsg.SERVER_ERROR, ex.getTargetException().getLocalizedMessage());

		}
		else {
			return Result.error(CodeMsg.SERVER_ERROR, Arrays.asList(e.getLocalizedMessage()));
		}
	}
}
