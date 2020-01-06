package com.jinxiang.order.dao;

import com.jinxiang.order.pojo.Customer;
import com.jinxiang.order.pojo.Order;
import com.jinxiang.order.tool.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
    int deleteByPrimaryKey(Long customerId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Order> selectBySelective(@Param("pager") Pager pager, @Param("customer") Customer customer);

    int selectCount(@Param("customer")Customer customer);
}