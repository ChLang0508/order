package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Commodity;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);
}