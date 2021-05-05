package com.nicico.cost.redis.service.impl;

import com.nicico.cost.framework.config.general.GeneralStatic;
import com.nicico.cost.framework.domain.dto.BaseDTO;
import com.nicico.cost.framework.enums.exception.ExceptionEnum;
import com.nicico.cost.framework.packages.redis.view.RedisResVM;
import com.nicico.cost.framework.service.exception.ApplicationException;
import com.nicico.cost.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.nicico.cost.framework.service.GeneralResponse.successCustomResponse;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ModelMapper modelMapper;
    private final ApplicationException applicationException;

    public BaseDTO<Boolean> setIn(String key, Object o) {
        redisTemplate.opsForValue().set(generateKey(key), o);
        return successCustomResponse(true);
    }


    public BaseDTO<Boolean> setIn(String key, Object o, Long expireTime) {
        redisTemplate.opsForValue().set(generateKey(key), o, expireTime, TimeUnit.MILLISECONDS);
        return successCustomResponse(true);
    }


    public BaseDTO<Object> updateIfPresent(String key, Object o) {
        Object obj = redisTemplate.opsForValue().getAndSet(generateKey(key), o);
        return successCustomResponse(obj);
    }


    @Async("treadPoolAsync")
    public void setAsyncIn(String key, Object o) {
        redisTemplate.opsForValue().set(generateKey(key), o);
    }

    @Async("treadPoolAsync")
    public void setAsyncIn(String key, Object o, Long expireTime) {
        redisTemplate.opsForValue().set(generateKey(key), o, expireTime, TimeUnit.MILLISECONDS);
    }

    @Async("treadPoolAsync")
    public void setAsyncInIfPresent(String key, Object o) {
        redisTemplate.opsForValue().setIfAbsent(generateKey(key), o);
    }


    public <T> BaseDTO<T> fetch(String key, Boolean expireAfterFetch, Class<T> tClass) {
        Object o = redisTemplate.opsForValue().get(generateKey(key));
        if (Boolean.TRUE.equals(expireAfterFetch))
            redisTemplate.delete(generateKey(key));
        if (o != null)
            return successCustomResponse(modelMapper.map(o, tClass));
        return successCustomResponse(null);
    }

    public BaseDTO<Object> fetch(String key, Boolean expireAfterFetch) {
        Object o = redisTemplate.opsForValue().get(generateKey(key));
        if (Boolean.TRUE.equals(expireAfterFetch))
            redisTemplate.delete(generateKey(key));
        return successCustomResponse(o);
    }

    public BaseDTO<RedisResVM> fetchComplete(String key, Boolean expireAfterFetch) {
        Object o = redisTemplate.opsForValue().get(generateKey(key));
        BaseDTO<Long> baseDTO = getExpireTime(key, TimeUnit.MILLISECONDS).orElseThrow(
                applicationException.createApplicationException(ExceptionEnum.NOTFOUND)
        );
        if (Boolean.TRUE.equals(expireAfterFetch))
            redisTemplate.delete(generateKey(key));
        RedisResVM redisResVM = RedisResVM.builder().object(o).expireTime(baseDTO.getData()).build();
        return successCustomResponse(redisResVM);
    }

    public BaseDTO<Long> getExpireTime(String key, TimeUnit timeUnit) {
        Long expire = redisTemplate.getExpire(generateKey(key), timeUnit);
        return successCustomResponse(expire).orElseThrow(
                applicationException.createApplicationException(ExceptionEnum.NOTFOUND)
        );
    }

    public BaseDTO<Boolean> delete(String key) {
        Boolean delete = redisTemplate.delete(generateKey(key));
        return successCustomResponse(delete);
    }

    private String generateKey(String key) {
        return GeneralStatic.applicationName.concat("-").concat(key);
    }
}
