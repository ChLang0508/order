package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.tool.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository()
public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int selectBySelective(@Param("pager")Pager pager,Order order);

    int selectCount(Order order);
}