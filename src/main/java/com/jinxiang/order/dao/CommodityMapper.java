package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Commodity;
import com.jinxiang.order.tool.Pager;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<Commodity> selectBySelective(@Param("pager") Pager pager, @Param("commodity") Commodity commodity);

    int selectCount(@Param("commodity")Commodity commodity);

    List<Commodity> selectByIDs(@Param("IDs") List<Long> IDs);



}