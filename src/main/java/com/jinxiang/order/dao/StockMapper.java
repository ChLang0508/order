package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.pojo.Stock;
import com.jinxiang.order.tool.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMapper {
    int deleteByPrimaryKey(Long ord);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long ord);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    Stock selectByCommodityId(@Param("commodityID") Long commodityID);

    List<Order> selectBySelective(@Param("pager") Pager pager, @Param("stock") Stock stock);

    int selectCount(@Param("stock")Stock stock);
}