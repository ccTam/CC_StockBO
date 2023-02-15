package com.example.stockbackoffice.dao;

import com.example.stockbackoffice.dto.StockDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
public class StockRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    int quarter;
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id")
    @NotNull
    StockSymbol stockSymbol;
    @NotNull
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
    @CreationTimestamp
    LocalDateTime created_date;
    @Column(columnDefinition = "varchar(100) DEFAULT 'SYSTEM'", nullable = false)
    String created_user = "SYSTEM";

    public StockDto toDto() {
        StockDto dto = new StockDto(quarter,
                stockSymbol.getSymbol(), date, open, high, low, close, volume, percent_change_price, percent_change_volume_over_last_wk, previous_weeks_volume, next_weeks_open, next_weeks_close, percent_change_next_weeks_price, days_to_next_dividend, percent_return_next_dividend);
        return dto;
    }
}
