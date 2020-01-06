package com.jinxiang.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.OrderDetail;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.CommodityService;
import com.jinxiang.order.service.CustomerService;
import com.jinxiang.order.service.OrderService;
import com.jinxiang.order.tool.Pager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Order order,
                                    @RequestParam("orderDetailStr") String orderDetailStr) throws Exception {
        List<OrderDetail> orderDetails = JSONArray.parseArray(orderDetailStr, OrderDetail.class);
        if (orderDetails.size() == 0) {
            return new ResponseEntity<>(400, false, "单据明细不能为空", null);
        }
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrd(null);
        }
        order.setCreateTime(new Date());
        order.setOrderId(null);
        order.setStatus(1);
        order.setTotalSum(null);
        if (order.getCustomerId() == null) {
            return new ResponseEntity<>(400, false, "请选择客户", null);
        }
        if (orderService.orderInsert(order, orderDetails)) {
            return new ResponseEntity<>(200, true, "添加成功", null);
        }
        return new ResponseEntity<>(500, false, "添加失败，为止原因", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Order order,
                                    @RequestParam(value = "orderDetailStr", required = false) String orderDetailStr) throws Exception {
        if (order.getOrderId() == null) {
            return new ResponseEntity<>(400, false, "请选择需要修改的单据", null);
        }

        order.setStatus(null);
        order.setCreateTime(null);
        order.setTotalSum(null);

        List<OrderDetail> orderDetails = new ArrayList<>();
        if (!StringUtils.isEmpty(orderDetailStr)) {
            orderDetails = JSONArray.parseArray(orderDetailStr, OrderDetail.class);
        }
        if (orderService.orderUpdate(order, orderDetails)) {
            return new ResponseEntity<>(200, true, "修改成功", null);
        }
        return new ResponseEntity<>(500, false, "修改失败，未知原因", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long orderID) throws Exception {
        if (orderService.del(orderID)) {
            return new ResponseEntity<>(200, true, "删除成功", null);
        }
        return new ResponseEntity<>(500, false, "删除失败，未知原因", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Order order) throws Exception {
        pager = orderService.getList(order, pager);
        return new ResponseEntity<>(200, true, "", pager);
    }
}
