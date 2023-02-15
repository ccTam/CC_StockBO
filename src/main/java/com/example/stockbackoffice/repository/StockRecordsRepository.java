package com.example.stockbackoffice.repository;

import com.example.stockbackoffice.dao.StockRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRecordsRepository extends JpaRepository<StockRecords, Long> {
    @Query(value = "SELECT * FROM stock_records sr WHERE sr.symbol_id = ?1", nativeQuery = true)
    List<StockRecords> getStockRecordBySymbolId(Long symbolId);
}
