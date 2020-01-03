package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Customer;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.tool.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Customer customer) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Customer customer) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long customerID) {
        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Customer customer) {
        return new ResponseEntity<>(200, true, "", pager);
    }
}
