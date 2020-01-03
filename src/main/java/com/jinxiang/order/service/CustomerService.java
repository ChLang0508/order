package com.jinxiang.order.service;

import com.jinxiang.order.pojo.Customer;
import com.jinxiang.order.tool.Pager;

public interface CustomerService {

    Boolean customerInsert(Customer customer) throws Exception;

    Boolean customerUpdate(Customer customer) throws Exception;

    Boolean del(Long customerID) throws Exception;

    Pager getList(Customer customer, Pager pager) throws Exception;
}
