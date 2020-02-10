package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Customer;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.CustomerService;
import com.jinxiang.order.tool.Pager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Customer customer) throws Exception {
        customer.setCustomerId(null);
        if (StringUtils.isEmpty(customer.getName())) {
            return new ResponseEntity<>(400, false, "客户名称不能为空", null);
        }
        if (StringUtils.isEmpty(customer.getPhone())) {
            return new ResponseEntity<>(400, false, "电话号码不能为空", null);
        }

        boolean isMatches = Pattern.matches(customer.getPhone(), "^[1]{10}$");
        if (!isMatches) {
            return new ResponseEntity<>(400, false, "请输入正确的手机号", null);
        }

        if (customerService.customerInsert(customer)) {
            return new ResponseEntity<>(200, true, "添加成功", null);
        }
        return new ResponseEntity<>(500, false, "添加失败，未知原因", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Customer customer) throws Exception {
        if (customer.getCustomerId() == null) {
            return new ResponseEntity<>(400, false, "请选择需要修改的客户", null);
        }
        if (!StringUtils.isEmpty(customer.getPhone())) {
            boolean isMatches = Pattern.matches(customer.getPhone(), "^[1]{10}$");
            if (!isMatches) {
                return new ResponseEntity<>(400, false, "请输入正确的手机号", null);
            }
        }
        if (customerService.customerUpdate(customer)) {
            return new ResponseEntity<>(200, true, "修改成功", null);
        }
        return new ResponseEntity<>(500, false, "修改失败，未知原因", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long customerID) throws Exception {
        if (customerService.del(customerID)) {
            return new ResponseEntity<>(200, true, "删除成功", null);
        }
        return new ResponseEntity<>(500, false, "删除失败，未知原因", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Customer customer) throws Exception {
        pager = customerService.getList(customer, pager);
        return new ResponseEntity<>(200, true, "查询成功", pager);
    }
}
