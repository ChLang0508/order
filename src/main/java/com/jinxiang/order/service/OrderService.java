package com.jinxiang.order.service;

import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.OrderDetail;
import com.jinxiang.order.tool.Pager;

public interface OrderService {

    Boolean orderInsert(Order order, OrderDetail orderDetail) throws Exception;

    Boolean orderUpdate(Order order, OrderDetail orderDetail) throws Exception;

    Boolean del(Long orderID) throws Exception;

    Pager getList(Order order, Pager pager) throws Exception;

}
