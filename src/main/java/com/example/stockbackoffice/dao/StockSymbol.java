package com.example.stockbackoffice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class StockSymbol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique=true, columnDefinition = "varchar(5)", nullable = false)
    String symbol;
    @CreationTimestamp
    LocalDateTime created_date;
    @Column(columnDefinition = "varchar(100) DEFAULT 'SYSTEM'", nullable = false)
    String created_user = "SYSTEM";
}
