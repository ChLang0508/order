package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.StockMapper;
import com.jinxiang.order.pojo.Stock;
import com.jinxiang.order.service.StockServices;
import com.jinxiang.order.tool.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockServicesImpl implements StockServices {

    @Resource
    private StockMapper stockMapper;

    @Override
    public Boolean stockInsert(Stock stock) throws Exception {
        return false;
    }

    @Override
    public Boolean stockUpdate(Stock stock) throws Exception {
        return false;
    }

    @Override
    public Boolean del(Long commodityID) throws Exception {
        return false;
    }

    @Override
    public Pager getList(Stock stock, Pager pager) throws Exception {
        return pager;
    }
}
