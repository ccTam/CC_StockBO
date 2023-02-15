package com.example.stockbackoffice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.io.BigDecimalParser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Data
public class StockDtoRequest {

    int quarter;
    String stock;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "M/d/yyyy")
    LocalDate date;
    String open;
    String high;
    String low;
    String close;
    BigInteger volume;
    BigDecimal percent_change_price;
    BigDecimal percent_change_volume_over_last_wk;
    BigDecimal previous_weeks_volume;
    String next_weeks_open;
    String next_weeks_close;
    BigDecimal percent_change_next_weeks_price;
    int days_to_next_dividend;
    BigDecimal percent_return_next_dividend;

    public StockDto toRealDto() {
        try {
            BigDecimal open = BigDecimalParser.parse(this.open.replace("$", ""));
            BigDecimal close = BigDecimalParser.parse(this.close.replace("$", ""));
            BigDecimal high = BigDecimalParser.parse(this.high.replace("$", ""));
            BigDecimal low = BigDecimalParser.parse(this.low.replace("$", ""));
            BigDecimal next_weeks_open = BigDecimalParser.parse(this.next_weeks_open.replace("$", ""));
            BigDecimal next_weeks_close = BigDecimalParser.parse(this.next_weeks_close.replace("$", ""));

            return new StockDto(quarter, stock, date, open, high, low, close, volume, percent_change_price, percent_change_volume_over_last_wk, previous_weeks_volume, next_weeks_open, next_weeks_close, percent_change_next_weeks_price, days_to_next_dividend, percent_return_next_dividend);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StockDtoRequest(String[] attr) {
        int cnt = 0;
        this.quarter = Integer.parseInt(attr[cnt]);
        this.stock = attr[++cnt];
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("M/d/yyyy");
        this.date = attr[++cnt].equals("") ? null : LocalDate.parse(attr[cnt], dt);
        this.open = attr[++cnt];
        this.high = attr[++cnt];
        this.low = attr[++cnt];
        this.close = attr[++cnt];
        this.volume = attr[++cnt].equals("") ? null : new BigInteger(attr[cnt]);
        this.percent_change_price = attr[++cnt].equals("") ? null : new BigDecimal(attr[cnt]);
        this.percent_change_volume_over_last_wk = attr[++cnt].equals("") ? null : new BigDecimal(attr[cnt]);
        this.previous_weeks_volume = attr[++cnt].equals("") ? null : new BigDecimal(attr[cnt]);
        this.next_weeks_open = attr[++cnt];
        this.next_weeks_close = attr[++cnt];
        this.percent_change_next_weeks_price = attr[++cnt].equals("") ? null : new BigDecimal(attr[cnt]);
        this.days_to_next_dividend = Integer.parseInt(attr[++cnt]);
        this.percent_return_next_dividend = attr[++cnt].equals("") ? null : new BigDecimal(attr[cnt]);
    }
}
