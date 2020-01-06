package com.jinxiang.order.service;

import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.OrderDetail;
import com.jinxiang.order.tool.Pager;

import java.util.List;

public interface OrderService {

    Boolean orderInsert(Order order, List<OrderDetail> orderDetails) throws Exception;

    Boolean orderUpdate(Order order, List<OrderDetail> orderDetails) throws Exception;

    Boolean del(Long orderID) throws Exception;

    Pager getList(Order order, Pager pager) throws Exception;

}
