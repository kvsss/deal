package com.deng.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Data
public class Transaction {

    private LocalDate day;
    private YearMonth dayDate;
    //private WeekOfYear week;
    private BigDecimal amounts;
    private Long counts;
}
