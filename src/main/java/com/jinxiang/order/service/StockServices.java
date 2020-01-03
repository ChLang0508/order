package com.jinxiang.order.service;

import com.jinxiang.order.pojo.Stock;
import com.jinxiang.order.tool.Pager;

public interface StockServices {

    Boolean stockInsert(Stock stock) throws Exception;

    Boolean stockUpdate(Stock stock) throws Exception;

    Boolean del(Long commodityID) throws Exception;

    Pager getList(Stock stock, Pager pager) throws Exception;
}
