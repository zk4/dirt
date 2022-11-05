package com.zk.dirt.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Option {
    String label;
    String value;
}