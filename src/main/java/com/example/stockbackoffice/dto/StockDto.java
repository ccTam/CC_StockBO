package com.example.stockbackoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDto {
    int quarter;
    String stock;
    LocalDate date;
    BigDecimal open;
    BigDecimal high;
    BigDecimal low;
    BigDecimal close;
    BigInteger volume;
    BigDecimal percent_change_price;
    BigDecimal percent_change_volume_over_last_wk;
    BigDecimal previous_weeks_volume;
    BigDecimal next_weeks_open;
    BigDecimal next_weeks_close;
    BigDecimal percent_change_next_weeks_price;
    int days_to_next_dividend;
    BigDecimal percent_return_next_dividend;

}
