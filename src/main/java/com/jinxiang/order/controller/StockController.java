package com.jinxiang.order.controller;

import com.jinxiang.order.pojo.Stock;
import com.jinxiang.order.pojo.ResponseEntity;
import com.jinxiang.order.service.StockServices;
import com.jinxiang.order.tool.Pager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private StockServices stockServices;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> insert(Stock stock) throws Exception {
        stock.setOrd(null);
        if (stock.getCommodity() == null) {
            return new ResponseEntity<>(400, false, "请选择存货", null);
        }
        if (stock.getStockNum() == null) {
            stock.setStockNum(new BigDecimal(0));
        }
        if (StringUtils.isEmpty(stock.getUnits())) {
            stock.setUnits("个");
        }
        if (stockServices.stockInsert(stock)) {
            return new ResponseEntity<>(200, true, "添加成功", null);
        }
        return new ResponseEntity<>(500, false, "添加失败，为止原因", null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(Stock stock) throws Exception {
        if (stock.getOrd() == null) {
            return new ResponseEntity<>(400, false, "请选择需要修改的记录", null);
        }
        stock.setCommodity(null);
        if (stockServices.stockUpdate(stock)) {
            return new ResponseEntity<>(200, true, "修改成功", null);
        }
        return new ResponseEntity<>(500, false, "修改失败，未知原因", null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<?> del(Long stockID) throws Exception {
        if (stockServices.del(stockID)) {
            return new ResponseEntity<>(200, true, "删除成功", null);
        }
        return new ResponseEntity<>(500, false, "删除失败，为止原因", null);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> getList(Pager pager, Stock stock) throws Exception {
        pager = stockServices.getList(stock, pager);
        return new ResponseEntity<>(200, true, "", pager);
    }

}
