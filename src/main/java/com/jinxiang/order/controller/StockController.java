package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Stock;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.StockServices;
import com.jinxiang.order.tool.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StockController {

    @Resource
    private StockServices stockServices;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Stock stock) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Stock stock) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long stockID) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Stock stock) {
        return new ResponseEntity<>(200, true, "", pager);
    }

}
