package com.mint.other.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cw
 * @Date: 2021/2/5 14:51
 * @Description:
 */

@Repository
public interface TestMapper {

    @Select({
            "<script>",
            "select",
            "*",
            "from delivery_spot",
            "where is_valid=1 and id in",
            "<foreach collection='rangeIds' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Object> deliverySpotsListByIds(@Param("rangeIds") List<Integer> rangeIds);

    @Select("select goods_id,goods_num,detail_img,goods_id from goods_detail where goods_id=#{goodsId} and is_valid=1")
    @Results(value = {
            @Result(property = "goodsId", column = "goods_id", javaType = Integer.class),
            @Result(property = "goodsNum", column = "goods_num", javaType = Integer.class),
            @Result(property = "detailImg", column = "detail_img", javaType = String.class),
            @Result(column = "goods_id", property = "goodsKindsVos", many = @Many(select = "com.xes.apply.mapper.ApplyMapper.getGoodsKindsVo"))
    })
    List<Object> getGoodsDetailsList(Integer goodsId);

    @Select("select id,kinds from goods_kinds where goods_id=#{goodsId} and is_valid=1")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "kinds", column = "kinds", javaType = String.class)
    })
    Object getGoodsKindsVo(Integer goodsId);
}

