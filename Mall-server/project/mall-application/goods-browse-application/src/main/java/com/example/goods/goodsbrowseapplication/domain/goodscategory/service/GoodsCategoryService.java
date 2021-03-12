package com.example.goods.goodsbrowseapplication.domain.goodscategory.service;

import com.example.goods.goodsbrowseapplication.domain.goodscategory.GoodsCategory;
import com.example.goods.goodsbrowseapplication.domain.goodscategory.entity.SpecsInfo;
import com.example.goods.goodsbrowseapplication.domain.goodscategory.factory.GoodsCategoryFactory;
import com.example.goods.goodsbrowseapplication.domain.goodscategory.po.GoodsCategoryPO;
import com.example.goods.goodsbrowseapplication.domain.goodscategory.repository.GoodsCategoryRepository;
import com.example.mallcommon.lazyload.TargetEnhancerContainer;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别领域服务
 *
 * <p>商品类别领域服务</p>
 *
 * @author WuHao
 * @since 2021/3/3 14:04
 */
@Service
public class GoodsCategoryService {

    @Autowired
    private ObjectFactory<TargetEnhancerContainer<Object>> containerFactory;
    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    /**
     * 获取所有的商品类别信息
     *
     * @return
     */
    public List<GoodsCategory> getCategories() {
        List<GoodsCategoryPO> goodsCategoryPOList = goodsCategoryRepository.findAllCategories();
        GoodsCategoryPO goodsCategoryPO = goodsCategoryPOList.get(0);
        List<GoodsCategory> goodsCategoryList = new ArrayList<>();

        TargetEnhancerContainer<Object> container = containerFactory.getObject();
        container.initialize(goodsCategoryPO, GoodsCategory.class);
        goodsCategoryList.add((GoodsCategory) container.getTarget());


        return goodsCategoryList;
//        List<GoodsCategory> goodsCategoryList = GoodsCategoryFactory.mulConvertGoodsCategoryFromPO(goodsCategoryPOList);
//        return GoodsCategoryFactory.mulConvertGoodsCategoryFromPO(goodsCategoryPOList);
    }


    public List<GoodsCategory> getSpecsInfos(Integer thirdCategoryId) {
        List<GoodsCategoryPO> goodsCategoryPOList = goodsCategoryRepository.findByThirdCategoryId(thirdCategoryId);
        List<GoodsCategory> goodsCategoryList = GoodsCategoryFactory.mulConvertGoodsCategoryFromPO(goodsCategoryPOList);
        return goodsCategoryList;
    }
}
