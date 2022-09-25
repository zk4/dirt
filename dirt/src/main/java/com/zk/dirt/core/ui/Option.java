package com.zk.dirt.core.ui;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.List;

@Data
public class Option {
	String value;
	String label;
	List<Option> children;
	Boolean isLeaf;
	Bool loading;

	public Option(String value, String label) {
		this.value = value;
		this.label = label;
	}
}
