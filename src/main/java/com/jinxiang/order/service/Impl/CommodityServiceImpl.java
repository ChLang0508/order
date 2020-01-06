package com.jinxiang.order.service.Impl;

import com.jinxiang.order.dao.CommodityMapper;
import com.jinxiang.order.dao.OrderDetailMapper;
import com.jinxiang.order.pojo.Commodity;
import com.jinxiang.order.service.CommodityService;
import com.jinxiang.order.tool.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Boolean commodityInsert(Commodity commodity) throws Exception {
        return commodityMapper.insertSelective(commodity) == 1;
    }

    @Override
    public Boolean commodityUpdate(Commodity commodity) throws Exception {
        if (commodityMapper.selectByPrimaryKey(commodity.getCommodityId()) == null) {
            throw new Exception("选择修改的商品不存在");
        }
        return commodityMapper.updateByPrimaryKeySelective(commodity) == 1;
    }

    @Override
    public Boolean del(Long commodityID) throws Exception {
        if (orderDetailMapper.selectByCommodityID(commodityID) != 0) {
            throw new Exception("商品已被其他订单使用，不能删除");
        }
        if (commodityMapper.selectByPrimaryKey(commodityID) == null) {
            throw new Exception("选择删除的商品不存在");
        }
        return commodityMapper.deleteByPrimaryKey(commodityID) == 1;
    }

    @Override
    public Pager getList(Commodity commodity, Pager pager) throws Exception {
        pager.setList(commodityMapper.selectBySelective(pager, commodity));
        pager.setTotalRow(commodityMapper.selectCount(commodity));
        return pager;
    }
}
