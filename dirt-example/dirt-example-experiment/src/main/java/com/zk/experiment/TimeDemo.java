//package com.zk.experiment;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.zk.dirt.annotation.DirtEntity;
//import com.zk.dirt.annotation.DirtField;
//import com.zk.dirt.annotation.DirtSearch;
//import com.zk.dirt.core.eUIType;
//import com.zk.dirt.entity.DirtBaseIdEntity;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.Entity;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//@Getter
//@Setter
//@Entity
//@DirtEntity
//@DynamicUpdate
//@DynamicInsert
//// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
//public class TimeDemo extends DirtBaseIdEntity {
//
//    @DirtField(title = "localDate",
//
//
//            uiType = eUIType.date,
//
//            dirtSearch = @DirtSearch(
//                    title = "时间范围",
//                    valueType = eUIType.dateRange
//            )
//    )
//    protected LocalDate localDate;
//
//    @DirtField(title = "localDateTime",
//
//
//            uiType = eUIType.dateTime,
//
//            dirtSearch = @DirtSearch(
//                    title = "时间范围",
//                    valueType = eUIType.dateTimeRange
//            )
//    )
//    protected LocalDateTime localDateTime;
//
//
//    @DirtField(title = "localTime",
//
//            uiType = eUIType.time,
//
//            dirtSearch = @DirtSearch(
//                    title = "时间范围",
//                    valueType = eUIType.timeRange
//            )
//    )
//    protected LocalTime localTime;
//
//
//}
