package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.*;
import com.jinxiang.order.pojo.*;
import com.jinxiang.order.service.OrderService;
import com.jinxiang.order.tool.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private CustomerMapper customerMapper;


    @Transactional(rollbackFor = Exception.class)
    public Boolean orderInsert(Order order, List<OrderDetail> orderDetails) throws Exception {
        Customer customer = customerMapper.selectByPrimaryKey(order.getCustomerId());
        if (customer == null) {
            throw new Exception("选择的客户不存在");
        }
        if (orderMapper.insertSelective(order) != 1) {
            throw new Exception("单据新增失败，未知错误");
        }
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail orderDetail = orderDetails.get(i);
            Commodity commodity = commodityMapper.selectByPrimaryKey(orderDetail.getCommodity());
            int j = i + 1;
            if (commodity == null) {
                throw new Exception("单据明细第" + j + "行商品不存在，请重新选择");
            }

            Stock stock = stockMapper.selectByCommodityId(orderDetail.getCommodity());
            if (stock == null || stock.getStockNum().compareTo(orderDetail.getCount()) < 0) {
                throw new Exception("商品库存不足，订单无法创建");
            }

            orderDetail.setOrderId(order.getOrderId());

            if (orderDetailMapper.insertSelective(orderDetail) != 1) {
                throw new Exception("单据明细第" + j + "行新增失败，未知错误");
            }

            Stock updateStock = new Stock();
            updateStock.setOrd(stock.getOrd());
            updateStock.setStockNum(stock.getStockNum().subtract(orderDetail.getCount()));
            if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                throw new Exception("库存更新失败");
            }
        }
        return true;
    }

    /**
     * 修改操作
     * 更新表体，不传ord为新增行，单传ord为删除行，传ord和其他数据为修改行
     *
     * @param order
     * @param orderDetails
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderUpdate(Order order, List<OrderDetail> orderDetails) throws Exception {
        if (orderMapper.selectByPrimaryKey(order.getOrderId()) == null) {
            throw new Exception("选择的单据不存在");
        }
        if (order.getCustomerId() != null) {
            Customer customer = customerMapper.selectByPrimaryKey(order.getCustomerId());
            if (customer == null) {
                throw new Exception("选择的客户不存在");
            }
            if (orderMapper.updateByPrimaryKeySelective(order) != 1) {
                throw new Exception("表头更新失败，未知错误");
            }
        }

        //更新表体，不传ord为新增行，单传ord为删除行，传ord和其他数据为修改行
        for (OrderDetail orderDetail : orderDetails) {

            orderDetail.setOrderId(null);
            //新增行
            if (orderDetail.getOrd() == null) {
                Commodity commodity = commodityMapper.selectByPrimaryKey(orderDetail.getCommodity());
                if (commodity == null) {
                    throw new Exception("新增条目商品不存在，请重新选择");
                }
                Stock stock = stockMapper.selectByCommodityId(orderDetail.getCommodity());
                if (stock == null || stock.getStockNum().compareTo(orderDetail.getCount()) < 0) {
                    throw new Exception("商品库存不足，订单无法修改");
                }

                orderDetail.setOrderId(order.getOrderId());
                if (orderDetailMapper.insertSelective(orderDetail) != 1) {
                    throw new Exception("单据明细新增失败，未知错误");
                }
                Stock updateStock = new Stock();
                updateStock.setOrd(stock.getOrd());
                updateStock.setStockNum(stock.getStockNum().subtract(orderDetail.getCount()));
                if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                    throw new Exception("库存更新失败");
                }
            } else {
                OrderDetail temp = orderDetailMapper.selectByPrimaryKey(orderDetail.getOrd());
                if (temp == null) {
                    throw new Exception("选择的明细不存在");
                }

                //删除行
                if (orderDetail.getCommodity() == null && orderDetail.getCount() == null) {

                    int delCount = orderDetailMapper.deleteByPrimaryKey(orderDetail.getOrd());
                    if (delCount != 1) {
                        throw new Exception("明细删除失败，未知原因");
                    }

                    //取库存
                    Stock stock = stockMapper.selectByCommodityId(temp.getCommodity());
                    Stock updateStock = new Stock();
                    updateStock.setOrd(stock.getOrd());
                    updateStock.setStockNum(stock.getStockNum().add(temp.getCount()));
                    if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                        throw new Exception("库存更新失败");
                    }
                } else {
                    //修改行
                    if (orderDetail.getCommodity() != null) {
                        Commodity commodity = commodityMapper.selectByPrimaryKey(orderDetail.getCommodity());
                        if (commodity == null) {
                            throw new Exception("修改条目商品不存在，请重新选择");
                        }

                    }

                    int updateCount = orderDetailMapper.updateByPrimaryKeySelective(orderDetail);
                    if (updateCount != 1) {
                        throw new Exception("明细修改失败，未知原因");
                    }

                    //需要修改商品
                    if (orderDetail.getCommodity() != null) {
                        Stock updateOldStock = new Stock();
                        Stock updateNewStock = new Stock();
                        Stock oldStock = stockMapper.selectByCommodityId(temp.getCommodity());
                        Stock newStock = stockMapper.selectByCommodityId(orderDetail.getCommodity());

                        if (newStock.getStockNum().compareTo(orderDetail.getCount()) < 0) {
                            throw new Exception("修改条目商品库存不足");
                        }
                        updateNewStock.setOrd(newStock.getOrd());
                        updateNewStock.setStockNum(newStock.getStockNum().subtract(orderDetail.getCount()));

                        updateOldStock.setOrd(oldStock.getOrd());
                        updateOldStock.setStockNum(oldStock.getStockNum().add(temp.getCount()));

                        if (stockMapper.updateByPrimaryKeySelective(updateOldStock) != 1) {
                            throw new Exception("库存更新失败");
                        }
                        if (stockMapper.updateByPrimaryKeySelective(updateNewStock) != 1) {
                            throw new Exception("库存更新失败");
                        }

                    } else {

                        //不改商品只改数量
                        Stock stock = stockMapper.selectByCommodityId(orderDetail.getCommodity());
                        BigDecimal count = stock.getStockNum().add(temp.getCount());
                        if (count.compareTo(orderDetail.getCount()) < 0) {
                            throw new Exception("修改条目商品库存不足");
                        }
                        Stock updateStock = new Stock();
                        updateStock.setOrd(stock.getOrd());
                        updateStock.setStockNum(count.subtract(orderDetail.getCount()));

                        if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                            throw new Exception("库存更新失败");
                        }
                    }
                }
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean del(Long orderID) throws Exception {
        if (orderMapper.selectByPrimaryKey(orderID) == null) {
            throw new Exception("选择的单据记录不存在");
        }
        int orderDetailCount = orderDetailMapper.delByOrderId(orderID);
        if (orderDetailCount == 0) {
            throw new Exception("没有单据明细被删除，请检查数据联系管理员");
        }
        return orderMapper.deleteByPrimaryKey(orderID) == 1;
    }

    public Pager getList(Order order, Pager pager) throws Exception {
        List<Order> orderList = orderMapper.selectBySelective(pager, order);
        for (Order value : orderList) {
            List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(value.getOrderId());
            value.setOrderDetailList(orderDetailList);
        }
        pager.setList(orderList);
        pager.setTotalRow(orderMapper.selectCount(order));
        return pager;
    }

}
