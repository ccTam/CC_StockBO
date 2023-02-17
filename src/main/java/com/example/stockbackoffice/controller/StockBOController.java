package com.example.stockbackoffice.controller;

import com.example.stockbackoffice.dto.StockDto;
import com.example.stockbackoffice.dto.StockDtoRequest;
import com.example.stockbackoffice.service.StockBOService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/stock")
@Slf4j
public class StockBOController {

    private final Logger logger = LoggerFactory.getLogger(StockBOController.class);
    @Autowired
    StockBOService stockBOService;


    @PostMapping("/batchUpload")
    private ResponseEntity<String> batchUpload(@RequestParam(name = "data") MultipartFile uploadedFile, @RequestParam(name = "startRow", required = false, defaultValue = "2") int startRow) {
        logger.info("Uploaded file={}", uploadedFile.getOriginalFilename());
        List<StockDto> list = null;
        try {
            list = stockBOService.readFromDataFile(uploadedFile, startRow);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        logger.info("Total processed record(s): {}", list.size());
        return new ResponseEntity<>(String.format("%s record(s) uploaded", list.size()), HttpStatus.OK);
    }

    @GetMapping("/{symbol}")
    private ResponseEntity<List<StockDto>> getStock(@PathVariable String symbol) {
        logger.info("getTicket={}", symbol);
        List<StockDto> stockList = stockBOService.getAllStockBySymbol(symbol);
        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<StockDto> createStock(@RequestBody StockDtoRequest stockDtoRequest) {
        StockDto dto = stockDtoRequest.toRealDto();
        logger.info("RequestBody={}", dto);
        stockBOService.AddRecord(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
