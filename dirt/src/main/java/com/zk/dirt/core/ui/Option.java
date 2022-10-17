package com.zk.dirt.core.ui;

import lombok.Data;

import java.util.List;

@Data
public class Option {
	String value;
	String label;
	List<Option> children;
	Boolean isLeaf;
	Boolean loading;

	public Option(String value, String label) {
		this.value = value;
		this.label = label;
	}
}
