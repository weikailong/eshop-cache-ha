package com.kaixin.eshop.cache.ha.cache.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 本地品牌缓存
 * @author: weikailong on 2019-03-11 23:01
 **/
public class BrandCache {
    
    private static Map<Long, String> brandMap = new ConcurrentHashMap<>();
    
    static{
        brandMap.put(1L,"iphone");
    }
    
    public static String getBrandName(Long brandId){
        return brandMap.get(brandId);
    }
    
}
