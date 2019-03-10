package com.kaixin.eshop.cache.ha.cache.local;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 本地地址名称缓存
 * @author: weikailong on 2019-03-11 00:13
 **/
public class LocationCache {
    
    private static Map<Long, String> cityMap = new ConcurrentHashMap<>();
    
    static {
        cityMap.put(1L, "北京");
    }
    
    public static String getCityName(Long cityId){
        return cityMap.get(cityId);
    }
    
}
