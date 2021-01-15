package com.zource.controller;

import com.zource.DTO.ProductDTO;
import com.zource.exceptions.product.ProductNotFoundException;
import com.zource.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class StockQuotesController {

    @GetMapping(value="/api/stock/{symbol}")

    @Transactional(readOnly = true)

    public ResponseEntity getById(@PathVariable(value = "symbol", required = true) String symbol) throws IOException {


        Stock stock = YahooFinance.get(symbol);

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        stock.print();

        Stock tesla = YahooFinance.get("TSLA", true);
        System.out.println(tesla.getHistory());




        return new ResponseEntity(stock.getHistory().toString(), HttpStatus.OK);

    }


}
