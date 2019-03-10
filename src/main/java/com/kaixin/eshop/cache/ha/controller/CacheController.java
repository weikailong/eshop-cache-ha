package com.kaixin.eshop.cache.ha.controller;

import com.kaixin.eshop.cache.ha.http.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 缓存controller
 * @author: weikailong on 2019-03-10 14:25
 **/
@Controller
public class CacheController {
    
    @RequestMapping("/change/product")
    @ResponseBody 
    public String changeProduct(Long productId){
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println(response);
        
        return "success";
    }
    
}
