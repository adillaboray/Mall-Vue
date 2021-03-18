package com.example.goods.goodsbrowseapplication.base.repository.goodscategory;

import com.example.goods.goodsbrowseapplication.base.repository.goodscategory.po.AvailableSpecsKeyPO;
import com.example.goods.goodsbrowseapplication.base.repository.goodscategory.po.CategoryInfoPO;
import com.example.goods.goodsbrowseapplication.base.repository.goodscategory.po.GoodsCategoryPO;
import com.example.goods.goodsbrowseapplication.base.repository.goodscategory.po.SpecsInfoPO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface GoodsCategoryMapper {
    @Select("SELECT" +
            " c.category_id AS third_category_id " +
            "FROM" +
            " goods_category a" +
            " LEFT JOIN goods_category b ON a.category_id = b.parent_id" +
            " LEFT JOIN goods_category c ON b.category_id = c.parent_id " +
            "WHERE" +
            " a.parent_id = 0 " +
            " and c.category_id IS NOT NULL")
    @Results({
            @Result(one = @One(select = "findAllCategoryInfoPO", fetchType = FetchType.LAZY), column = "third_category_id",
                    property = "categoryInfoPO"),
            @Result(many = @Many(select = "findSpecsInfoPOByThirdCategoryId", fetchType = FetchType.LAZY), column = "third_category_id",
                    property = "specsInfo", javaType = List.class)
    })
    List<GoodsCategoryPO> findAll();


    @Select("SELECT" +
            " a.category_name AS root_category_name," +
            " a.category_id AS root_category_id," +
            " b.category_name AS sec_category_name," +
            " b.category_id AS sec_category_id," +
            " c.category_name AS third_category_name," +
            " c.category_id AS third_category_id " +
            "FROM" +
            " goods_category a" +
            " LEFT JOIN goods_category b ON a.category_id = b.parent_id" +
            " LEFT JOIN goods_category c ON b.category_id = c.parent_id " +
            "WHERE" +
            " a.parent_id = 0 " +
            " and c.category_id = #{Integer thirdCategoryId}")
    CategoryInfoPO findAllCategoryInfoPO(Integer thirdCategoryId);

//    @Select("SELECT" +
//            " a.category_name AS root_category_name," +
//            " a.category_id AS root_category_id," +
//            " b.category_name AS sec_category_name," +
//            " b.category_id AS sec_category_id," +
//            " c.category_name AS third_category_name," +
//            " c.category_id AS third_category_id " +
//            "FROM" +
//            " goods_category a" +
//            " LEFT JOIN goods_category b ON a.category_id = b.parent_id" +
//            " LEFT JOIN goods_category c ON b.category_id = c.parent_id " +
//            "WHERE" +
//            " a.parent_id = 0 " +
//            " and c.category_id = #{thirdCategoryId}")
//    CategoryInfoPO findCategoryInfoPOByThirdCategoryId(Integer thirdCategoryId);


    @Select("select * from goods_category_specs_info where category_id = #{thirdCategoryId}")
    @Results({
            @Result(many = @Many(select = "findAvailableSpecsKeyPOBySpecsInfoId", fetchType = FetchType.LAZY), column = "specs_info_id",
                    property = "specsValue", javaType = List.class),
            @Result(column = "specs_info_id",property = "specsInfoId")
    })
    SpecsInfoPO findSpecsInfoPOByThirdCategoryId(Integer thirdCategoryId);

    @Select("select * from goods_category_specs_value where specs_info_id = #{specsInfoId}")
    AvailableSpecsKeyPO findAvailableSpecsKeyPOBySpecsInfoId(Integer specsInfoId);
}
