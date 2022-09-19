package com.zk;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class DataHolder {
    LocalDateTime localDateTime;
    LocalDate localDate;
    Date date;
    LocalTime localTime;
}
