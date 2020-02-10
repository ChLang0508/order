package com.jinxiang.order.dao;


import com.jinxiang.order.pojo.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long ord);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long ord);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    List<OrderDetail> selectByOrderId(@Param("orderID") Long orderID);

    int selectByCommodityID(@Param("commodityID") Long commodityID);

    int delByOrderId(@Param("orderID") Long orderID);

    int batchInsert(@Param("orderDetails") List<OrderDetail> orderDetails);

    List<OrderDetail> selectByOrderIDs(@Param("IDs") List<Long> IDs);

    int delByIDs(@Param("IDs") List<Long> IDs);
}