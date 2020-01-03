package com.jinxiang.order.pojo;

import java.math.BigDecimal;

public class Stock {
    private Long ord;

    private Long commodity;

    private BigDecimal stockNum;

    private String units;

    public Long getOrd() {
        return ord;
    }

    public void setOrd(Long ord) {
        this.ord = ord;
    }

    public Long getCommodity() {
        return commodity;
    }

    public void setCommodity(Long commodity) {
        this.commodity = commodity;
    }

    public BigDecimal getStockNum() {
        return stockNum;
    }

    public void setStockNum(BigDecimal stockNum) {
        this.stockNum = stockNum;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units == null ? null : units.trim();
    }
}