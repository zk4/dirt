package com.zk.config.rest.rsql;

import com.turkraft.springfilter.boot.FilterSpecification;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * 将 url 的 filter 转化为 hibernate 的 specification
 */
@Data
public class QueryFilter {
	@ApiModelProperty(value = "过滤字段，详见: https://github.com/turkraft/spring-filter",example ="" )
	private String filter;

	public QueryFilter() {}

	public QueryFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public final<T> Specification<T> getSpec(){
		if(StringUtils.isEmpty(filter))return null;
		return   new FilterSpecification(filter);

	}

}
