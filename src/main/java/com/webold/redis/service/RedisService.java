package com.webold.redis.service;

import com.webold.framework.domain.dto.BaseDTO;
import com.webold.framework.packages.redis.Redis;
import com.webold.framework.packages.redis.view.RedisResVM;

import java.util.concurrent.TimeUnit;

/**
 * @author nima mousavi
 * @version 1.0.1
 * @implNote redis service as interface of method that impl in redis library
 * @since 1.0.1
 */
public interface RedisService extends Redis {
    /**
     * @param key is the unique key in redis service
     * @param o   is object that you want to save in value
     * @return boolean of result
     */
    BaseDTO<Boolean> setIn(String key, Object o);

    /**
     * @param key        is the unique key in redis service
     * @param o          is object that you want to save in value
     * @param expireTime that is expireTime that the key not value after that
     * @return boolean of result
     */

    BaseDTO<Boolean> setIn(String key, Object o, Long expireTime);

    /**
     * @param key is the unique key in redis service
     * @param o   is object that you want to save in value
     * @return boolean for result
     */
    BaseDTO<Object> updateIfPresent(String key, Object o);

    /**
     * @param key is the unique key in redis service
     * @param o   is object that you want to save in value
     * @apiNote this methode call async
     */
    void setAsyncIn(String key, Object o);

    /**
     * @param key        is the unique key in redis service
     * @param o          is object that you want to save in value
     * @param expireTime that is expireTime that the key not value after that
     * @apiNote this methode call async
     */
    void setAsyncIn(String key, Object o, Long expireTime);

    /**
     * @param key is the unique key in redis service
     * @param o   is object that you want to save in value
     */

    void setAsyncInIfPresent(String key, Object o);

    /**
     * @param key              is the unique key in redis service
     * @param expireAfterFetch if true , delete key after fetch
     * @param tClass           the object you want to cast to it
     * @param <R>              the type of class
     */

    <R> BaseDTO<R> fetch(String key, Boolean expireAfterFetch, Class<R> tClass);

    /**
     * @param key              is the unique key in redis service
     * @param expireAfterFetch if true , delete key after fetch
     */
    BaseDTO<Object> fetch(String key, Boolean expireAfterFetch);

    /**
     * @param key              is the unique key in redis service
     * @param expireAfterFetch if true , delete key after fetch
     * @return BaseDTO<RedisResVM> is a view that all data such as header , expire time and etc
     */

    BaseDTO<RedisResVM> fetchComplete(String key, Boolean expireAfterFetch);

    /**
     * @param key      is the unique key in redis service
     * @param timeUnit the type of time you want fetch expire time
     */

    BaseDTO<Long> getExpireTime(String key, TimeUnit timeUnit);

    /**
     * @param key is the unique key in redis service
     * @return boolean for result
     */

    BaseDTO<Boolean> delete(String key);
}
