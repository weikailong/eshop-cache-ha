package com.kaixin.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.kaixin.eshop.cache.ha.http.HttpClientUtils;
import com.kaixin.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @description: 获取商品信息
 * @author: weikailong on 2019-03-10 15:12
 **/
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;
    
    public GetProductInfoCommand(Long productId) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoCommandGroup"));
        this.productId =productId;
    }

    @Override
    protected ProductInfo run() throws Exception {

        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        return JSONObject.parseObject(response, ProductInfo.class);
    }
}
