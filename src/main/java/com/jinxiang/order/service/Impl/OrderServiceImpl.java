package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.OrderDetailMapper;
import com.jinxiang.order.dao.OrderMapper;
import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.OrderDetail;
import com.jinxiang.order.service.OrderService;
import com.jinxiang.order.service.StockServices;
import com.jinxiang.order.tool.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private StockServices stockServices;

    @Transactional(rollbackFor = Exception.class)
    public Boolean orderInsert(Order order, OrderDetail orderDetail) throws Exception {
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean orderUpdate(Order order, OrderDetail orderDetail) throws Exception {
        return false;
    }

    public Boolean del(Long orderID) throws Exception {
        return false;
    }

    public Pager getList(Order order, Pager pager) throws Exception {
        return pager;
    }

}
