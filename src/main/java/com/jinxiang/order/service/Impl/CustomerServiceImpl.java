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
        return customerMapper.insertSelective(customer) == 1;
    }

    @Override
    public Boolean customerUpdate(Customer customer) throws Exception {
        if (customerMapper.selectByPrimaryKey(customer.getCustomerId()) == null) {
            throw new Exception("选择的客户不存在");
        }
        return customerMapper.updateByPrimaryKeySelective(customer) == 1;
    }

    @Override
    public Boolean del(Long customerID) throws Exception {
        if (orderMapper.selectByCustomerID(customerID) != 0) {
            throw new Exception("所选择的客户存在单据，不允许删除");
        }
        return customerMapper.deleteByPrimaryKey(customerID) == 1;
    }

    @Override
    public Pager getList(Customer customer, Pager pager) throws Exception {

        pager.setList(customerMapper.selectBySelective(pager, customer));
        pager.setTotalRow(customerMapper.selectCount(customer));
        return pager;
    }
}
