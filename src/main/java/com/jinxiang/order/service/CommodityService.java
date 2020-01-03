package com.jinxiang.order.service;

import com.jinxiang.order.pojo.Commodity;

import com.jinxiang.order.tool.Pager;

public interface CommodityService {

    Boolean commodityInsert(Commodity commodity) throws Exception;

    Boolean commodityUpdate(Commodity commodity) throws Exception;

    Boolean del(Long commodityID) throws Exception;

    Pager getList(Commodity commodity, Pager pager) throws Exception;
}
