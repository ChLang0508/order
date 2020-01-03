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
        return false;
    }

    @Override
    public Boolean commodityUpdate(Commodity commodity) throws Exception {
        return false;
    }

    @Override
    public Boolean del(Long commodityID) throws Exception {
        return false;
    }

    @Override
    public Pager getList(Commodity commodity, Pager pager) throws Exception {
        return pager;
    }
}
