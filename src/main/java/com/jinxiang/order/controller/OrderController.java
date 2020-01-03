package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.CommodityService;
import com.jinxiang.order.service.CustomerService;
import com.jinxiang.order.service.OrderService;
import com.jinxiang.order.tool.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

//    @Resource
//    private CommodityService commodityService;
//    @Resource
//    private CustomerService customerService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Order order) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Order order) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long orderID) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Order order) {
        return new ResponseEntity<>(200, true, "", pager);
    }
}
