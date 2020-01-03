package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {
    int deleteByPrimaryKey(Long ord);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long ord);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    Stock selectByCommodityId(@Param("commodityID") Long commodityID);
}