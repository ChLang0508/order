package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Commodity;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.CommodityService;
import com.jinxiang.order.tool.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    private CommodityService commodityService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Commodity commodity) throws Exception {
        commodity.setCommodityId(null);
        Boolean result = commodityService.commodityInsert(commodity);
        if (result) {
            return new ResponseEntity<>(200, true, "添加成功", null);
        } else {
            return new ResponseEntity<>(200, true, "添加失败，未知原因", null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Commodity commodity) throws Exception {

        if(commodity.getCommodityId()==null){
            return new ResponseEntity<>(200, true, "请选择需要修改的商品", null);
        }

        Boolean result = commodityService.commodityUpdate(commodity);
        if (result) {
            return new ResponseEntity<>(200, true, "修改成功", null);
        } else {
            return new ResponseEntity<>(200, true, "修改失败，未知原因", null);
        }
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long commodityID) throws Exception {
        Boolean result = commodityService.del(commodityID);
        if (result) {
            return new ResponseEntity<>(200, true, "删除成功", null);
        } else {
            return new ResponseEntity<>(200, true, "删除失败，未知原因", null);
        }
//        return new ResponseEntity<>(200, true, "", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Commodity commodity) throws Exception {
        pager = commodityService.getList(commodity, pager);
        return new ResponseEntity<>(200, true, "查询成功", pager);
    }


}
