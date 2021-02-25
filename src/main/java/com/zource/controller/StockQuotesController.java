package com.zource.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class StockQuotesController {

    @GetMapping(value = "/api/stock/{symbol}")
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


    @GetMapping(value = "/api/stock/{symbol}/price")
    @Transactional(readOnly = true)
    public ResponseEntity getStockPrice(@PathVariable(value = "symbol", required = true) String symbol) throws IOException {

        Stock stock = YahooFinance.get(symbol);

        return new ResponseEntity(stock.getQuote().getPrice(), HttpStatus.OK);

    }

    @GetMapping(value = "/api/stock/{symbol}/historicalQuotes")
    @Transactional(readOnly = true)
    public String getHistoricalQuotes(@PathVariable(value = "symbol", required = true) String symbol) throws IOException {

        List<List<String>> list = new ArrayList<List<String>>();

        Stock stock = YahooFinance.get(symbol, true);
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5);
        List<HistoricalQuote> stockHistQuotes = stock.getHistory(from, to, Interval.DAILY);

        System.out.println(stock.getHistory());

        for (HistoricalQuote h : stockHistQuotes) {
            List<String> l = new ArrayList<>();
            l.add(h.getDate().getTimeInMillis() + "");
            l.add(h.getClose().toString());
            list.add(l);
        }

        return list.toString().replace("\"", "");
      /*  return new ResponseEntity(stockHistQuotes.stream().collect(Collectors.toMap(a ->
                a.getDate().getTimeInMillis(), HistoricalQuote::getClose)), HttpStatus.OK);*/

    }

/*    @GetMapping(value = "/api/stock/{symbol}/historicalQuotes")
    @Transactional(readOnly = true)
    public ResponseEntity getHistoricalQuotes(@PathVariable(value = "symbol", required = true) String symbol) throws IOException {


        Stock stock = YahooFinance.get(symbol, true);
        List<HistoricalQuote> stockHistQuotes = stock.getHistory();



        System.out.println(stock.getHistory());

        Map<Long, BigDecimal> map = new HashMap();
           for (HistoricalQuote h: stockHistQuotes) {
               map.put(h.getDate().getTimeInMillis(), h.getClose());

           }
        List<StockObj> list = new ArrayList();

           for (HistoricalQuote h: stockHistQuotes) {

               list.add(new StockObj(h.getDate().getTimeInMillis(), h.getClose()));
           }

    *//*    return new ResponseEntity(list, HttpStatus.OK);*//*

        return new ResponseEntity(stockHistQuotes.stream().collect(Collectors.toMap(a ->
                a.getDate().getTimeInMillis(), HistoricalQuote::getClose)), HttpStatus.OK);

    }*/


    @GetMapping(value = "/api/stock/test")
    @Transactional(readOnly = true)
    public int[][] getTest() throws IOException, ParseException {


        String str = "Jun 13 2003 23:11:52.454 UTC";
        /*String str = "Jun 13 2003 23:11:52.454 UTC";*/
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
        Date date = df.parse(str);
        long epoch = date.getTime();
        System.out.println(epoch); // 1055545912454

        int[][] x = {{1, 2}, {3, 4}};
        return x;
        /*return new ResponseEntity(epoch, HttpStatus.OK);*/
    }

    @Data
    @AllArgsConstructor
    class StockObj {
        Long time;
        BigDecimal price;
    }
}
