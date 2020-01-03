package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.CustomerMapper;
import com.jinxiang.order.dao.OrderMapper;
import com.jinxiang.order.pojo.Customer;
import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.service.CustomerService;
import com.jinxiang.order.tool.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)

public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private OrderMapper orderMapper;


    @Override
    public Boolean customerInsert(Customer customer) throws Exception {
        return null;
    }

    @Override
    public Boolean customerUpdate(Customer customer) throws Exception {
        return null;
    }

    @Override
    public Boolean del(Long customerID) throws Exception {
        return null;
    }

    @Override
    public Pager getList(Customer customer, Pager pager) throws Exception {
        return null;
    }
}
