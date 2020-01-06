package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.CommodityMapper;
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
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public Boolean stockInsert(Stock stock) throws Exception {
        if (commodityMapper.selectByPrimaryKey(stock.getCommodity()) == null) {
            throw new Exception("所选的商品不存在");
        }
        return stockMapper.insertSelective(stock) == 1;
    }

    @Override
    public Boolean stockUpdate(Stock stock) throws Exception {
        if (stockMapper.selectByPrimaryKey(stock.getOrd()) == null) {
            throw new Exception("选择的库存记录不存在");
        }
        return stockMapper.updateByPrimaryKeySelective(stock) == 1;
    }

    @Override
    public Boolean del(Long commodityID) throws Exception {
        if (stockMapper.selectByPrimaryKey(commodityID) == null) {
            throw new Exception("选择的库存记录不存在");
        }
        return commodityMapper.deleteByPrimaryKey(commodityID) == 1;
    }

    @Override
    public Pager getList(Stock stock, Pager pager) throws Exception {
        pager.setList(stockMapper.selectBySelective(pager, stock));
        pager.setTotalRow(stockMapper.selectCount(stock));
        return pager;
    }
}
