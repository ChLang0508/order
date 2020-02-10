package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.*;
import com.jinxiang.order.pojo.*;
import com.jinxiang.order.tool.Pager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Primary
@Service
public class OrderServiceNewImpl extends OrderServiceImpl {

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderInsert(Order order, List<OrderDetail> orderDetails) throws Exception {
        Customer customer = customerMapper.selectByPrimaryKey(order.getCustomerId());
        if (customer == null) {
            throw new Exception("选择的客户不存在");
        }

        if (orderMapper.insertSelective(order) != 1) {
            throw new Exception("单据新增失败，未知错误");
        }


        //批量查询对比数据
        List<Long> commodityIds = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail orderDetail = orderDetails.get(i);
            Long commodityId = orderDetail.getCommodity();
            commodityIds.add(commodityId);
        }
        int result = commodityMapper.selectByIDs(commodityIds).size();
        if (result != commodityIds.size()) {
            throw new Exception("单据明细有商品不存在，或存在重复行，请检查数据");
        }
        //库存检查和更新，不批量
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail orderDetail = orderDetails.get(i);
            Stock stock = stockMapper.selectByCommodityId(orderDetail.getCommodity());
            if (stock == null || stock.getStockNum().compareTo(orderDetail.getCount()) < 0) {
                throw new Exception("商品库存不足，订单无法创建");
            }
            orderDetail.setOrderId(order.getOrderId());

            Stock updateStock = new Stock();
            updateStock.setOrd(stock.getOrd());
            updateStock.setStockNum(stock.getStockNum().subtract(orderDetail.getCount()));
            if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                throw new Exception("库存更新失败");
            }
        }
        //批量添加
        if (orderDetailMapper.batchInsert(orderDetails) != orderDetails.size()) {
            throw new Exception("单据明细添加失败，未知错误");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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

        List<OrderDetail> delList = new ArrayList<>();
        List<OrderDetail> updateList = new ArrayList<>();
        List<OrderDetail> insertList = new ArrayList<>();

        //更新表体，不传ord为新增行，单传ord为删除行，传ord和其他数据为修改行
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getOrderId());
            if (orderDetail.getOrd() == null) {
                insertList.add(orderDetail);
            } else {

                if (orderDetail.getCommodity() == null && orderDetail.getCount() == null) {
                    delList.add(orderDetail);
                } else {
                    updateList.add(orderDetail);
                }
            }
        }

        Boolean updateResult = updateDetail(updateList);
        Boolean delResult = delDetail(delList);
        Boolean insertResult = insertDetail(insertList);

        return updateResult && delResult && insertResult;
    }

    /**
     * 明细更新
     * @param list 更新列表
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected Boolean updateDetail(List<OrderDetail> list) throws Exception {

        for (OrderDetail orderDetail : list) {
            //修改行
            if (orderDetail.getCommodity() != null) {
                Commodity commodity = commodityMapper.selectByPrimaryKey(orderDetail.getCommodity());
                if (commodity == null) {
                    throw new Exception("修改条目商品不存在，请重新选择");
                }
            }
            OrderDetail temp = orderDetailMapper.selectByPrimaryKey(orderDetail.getOrd());
            if (temp == null) {
                throw new Exception("选择的明细不存在");
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
        return true;
    }

    /**
     * 表单修改时的明细新增
     * @param list 添加的明细列表
     * @return  true和false
     * @throws Exception 错误提示信息
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected Boolean insertDetail(List<OrderDetail> list) throws Exception {

        int insertResult = orderDetailMapper.batchInsert(list);
        if (insertResult != list.size()) {
            throw new Exception("明细修改失败，请检查数据");
        }
        for (OrderDetail orderDetail : list) {

            if (orderDetail.getOrd() == null) {

                Stock stock = getCommodityStock(orderDetail);

                Stock updateStock = new Stock();
                updateStock.setOrd(stock.getOrd());
                updateStock.setStockNum(stock.getStockNum().subtract(orderDetail.getCount()));
                if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                    throw new Exception("库存更新失败");
                }
            }
        }
        return true;
    }

    private Stock getCommodityStock(OrderDetail orderDetail) throws Exception {
        Commodity commodity = commodityMapper.selectByPrimaryKey(orderDetail.getCommodity());
        if (commodity == null) {
            throw new Exception("商品不存在，请重新选择");
        }
        Stock stock = stockMapper.selectByCommodityId(orderDetail.getCommodity());
        if (stock == null || stock.getStockNum().compareTo(orderDetail.getCount()) < 0) {
            throw new Exception("商品库存不足，订单无法修改");
        }
        return stock;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected Boolean delDetail(List<OrderDetail> list) throws Exception {

        List<Long> ordList = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            ordList.add(orderDetail.getOrd());
        }
        int delCount = orderDetailMapper.delByIDs(ordList);
        if (delCount != ordList.size()) {
            throw new Exception("有明细删除失败，请检查数据");
        }
        for (OrderDetail orderDetail : list) {
            OrderDetail temp = orderDetailMapper.selectByPrimaryKey(orderDetail.getOrd());
            if (temp == null) {
                throw new Exception("选择的明细不存在");
            }
            //取库存
            Stock stock = stockMapper.selectByCommodityId(temp.getCommodity());
            Stock updateStock = new Stock();
            updateStock.setOrd(stock.getOrd());
            updateStock.setStockNum(stock.getStockNum().add(temp.getCount()));
            if (stockMapper.updateByPrimaryKeySelective(updateStock) != 1) {
                throw new Exception("库存更新失败");
            }
        }
        return true;
    }

    @Override
    public Pager getList(Order order, Pager pager) throws Exception {
        Long start = System.currentTimeMillis();

        List<Order> orderList = orderMapper.selectBySelective(pager, order);
        List<Long> orderIDs = new ArrayList<>();
        for (Order temp : orderList) {
            orderIDs.add(temp.getOrderId());
        }
        List<OrderDetail> list = orderDetailMapper.selectByOrderIDs(orderIDs);


        for (Order value : orderList) {
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (OrderDetail orderDetail : list) {
                if (orderDetail.getOrderId().equals(value.getOrderId())) {
                    orderDetailList.add(orderDetail);
                }
            }
            value.setOrderDetailList(orderDetailList);
        }
        pager.setList(orderList);
        pager.setTotalRow(orderMapper.selectCount(order));


        Long end = System.currentTimeMillis();
        System.out.println(end - start);
        return pager;
    }
}
