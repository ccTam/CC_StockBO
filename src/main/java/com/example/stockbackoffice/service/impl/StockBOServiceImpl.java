package com.example.stockbackoffice.service.impl;

import com.example.stockbackoffice.dao.StockRecords;
import com.example.stockbackoffice.dao.StockSymbol;
import com.example.stockbackoffice.dto.StockDto;
import com.example.stockbackoffice.dto.StockDtoRequest;
import com.example.stockbackoffice.repository.StockRecordsRepository;
import com.example.stockbackoffice.repository.StockSymbolRepository;
import com.example.stockbackoffice.service.StockBOService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockBOServiceImpl implements StockBOService {

    private final Logger logger = LoggerFactory.getLogger(StockBOServiceImpl.class);
    @Autowired
    StockRecordsRepository stockRecordsRepository;
    @Autowired
    StockSymbolRepository stockSymbolRepository;

    @Value("${server.tmpFile.path}")
    String TMP_FILE_PATH;

    @Override
    public List<StockDto> readFromDataFile(MultipartFile uploadedFile, int startRow) {
        Path dirPath = new File(System.getProperty("user.dir") + TMP_FILE_PATH).toPath();
        Path filePath = new File(System.getProperty("user.dir") + TMP_FILE_PATH + File.separator + uploadedFile.getOriginalFilename()).toPath();
        try {
            multipartFileToFile(uploadedFile, dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return processFile(filePath, startRow);
    }

    @Override
    public StockDto AddRecord(StockDto dto) {
        StockSymbol ssDao = new StockSymbol();
        List<StockSymbol> ssList = stockSymbolRepository.getStockSymbolBySymbol(dto.getStock());
        if (ssList.size() == 0) {
            ssDao.setSymbol(dto.getStock());
            ssDao = stockSymbolRepository.save(ssDao);
        } else {
            ssDao = ssList.get(0);
        }
        StockRecords srDao = new StockRecords();
        srDao.setQuarter(dto.getQuarter());
        srDao.setStockSymbol(ssDao);
        srDao.setDate(dto.getDate());
        srDao.setOpen(dto.getOpen());
        srDao.setHigh(dto.getHigh());
        srDao.setLow(dto.getLow());
        srDao.setClose(dto.getClose());
        srDao.setVolume(dto.getVolume());
        srDao.setPercent_change_price(dto.getPercent_change_price());
        srDao.setPercent_change_volume_over_last_wk(dto.getPercent_change_volume_over_last_wk());
        srDao.setPrevious_weeks_volume(dto.getPrevious_weeks_volume());
        srDao.setNext_weeks_open(dto.getNext_weeks_open());
        srDao.setNext_weeks_close(dto.getNext_weeks_close());
        srDao.setPercent_change_next_weeks_price(dto.getPercent_change_next_weeks_price());
        srDao.setDays_to_next_dividend(dto.getDays_to_next_dividend());

        stockRecordsRepository.save(srDao);
        return dto;
    }

    @Override
    public List<StockDto> getAllStockBySymbol(String symbol) {
        List<StockSymbol> symbolList = stockSymbolRepository.getStockSymbolBySymbol(symbol);
        List<StockDto> stockDtoList = new ArrayList<>();
        for (StockSymbol ss : symbolList) {
            List<StockRecords> srList = stockRecordsRepository.getStockRecordBySymbolId(ss.getId());
            for (StockRecords sr : srList) {
                stockDtoList.add(sr.toDto());
            }
        }
        return stockDtoList;
    }

    private List<StockDto> processFile(Path filePath, int startRow) {
        List<StockDto> StockDtoList = new ArrayList<>();
        if (startRow < 2)
            startRow = 2;
        int curRow = 2; //Keeping track on which row to process (ignore the first title row)
        if (Files.exists(filePath)) {
            try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                String line = br.readLine(); //Skip the first row
                line = br.readLine();
                while (line != null) { // EoF will be null
                    if (curRow < startRow) {
                        line = br.readLine();
                        curRow++;
                        continue;
                    }
                    String[] attributes = line.split(",");
                    StockDto stockDto = new StockDtoRequest(attributes).toRealDto();
                    AddRecord(stockDto);
                    StockDtoList.add(stockDto);
                    logger.debug("processFile-processed line {}", curRow);
                    line = br.readLine();
                    curRow++;
                }
            } catch (Exception e) {
                logger.error("processFile-Error occurred when reading line: {}", curRow);
                e.printStackTrace();
            }
        }
        return StockDtoList;
    }

    private void multipartFileToFile(MultipartFile multipart, Path dir) throws IOException {
        Files.createDirectories(dir);
        Path filepath = Paths.get(dir.toString(), multipart.getOriginalFilename());
        multipart.transferTo(filepath);
    }
}
