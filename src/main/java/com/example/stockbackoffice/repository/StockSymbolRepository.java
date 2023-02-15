package com.example.stockbackoffice.repository;

import com.example.stockbackoffice.dao.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbol, Long> {
    @Query(value = "SELECT * FROM stock_symbol ss WHERE ss.symbol = ?1", nativeQuery = true)
    List<StockSymbol> getStockSymbolBySymbol(String symbol);

    @Query(value = "SELECT * FROM stock_symbol ss", nativeQuery = true)
    List<StockSymbol> getAll();
}
