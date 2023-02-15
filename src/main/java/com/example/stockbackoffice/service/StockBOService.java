package com.example.stockbackoffice.service;

import com.example.stockbackoffice.dto.StockDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockBOService {
    List<StockDto> readFromDataFile(MultipartFile uploadedFile, int startRow);

    StockDto AddRecord(StockDto dto);

    List<StockDto> getAllStockBySymbol(String symbol);
}
