### this interface used for connect to redis and implement in redis project

### Requirements
The library works with Java 8+, ladder Core 1.0.1+

## [Core](https://github.com/nimamoosavi/core/wiki)

## Structure
this project implement redis command line interface and you can connect to redis server with this project

[[/wiki/images/redis.png | "redis Diagram"]]


## method

BaseDTO< Boolean > setIn(String key, Object o)
> key is the unique key in redis service
>
> o   is object that you want to save in value
>
> return boolean of result

BaseDTO< Boolean > setIn(String key, Object o, Long expireTime)
> key        is the unique key in redis service
>
> o         is object that you want to save in value
>
> expireTime that is expireTime that the key not value after that
>
> return boolean of result

BaseDTO< Object > updateIfPresent(String key, Object o)
> key : is the unique key in redis service
>
> o :  is object that you want to save in value
>
>return boolean for result

void setAsyncIn(String key, Object o)
> key is the unique key in redis service
>
> o  : is object that you want to save in value
>
> this methode call async

void setAsyncIn(String key, Object o, Long expireTime)
> key  :      is the unique key in redis service
>
> o    :      is object that you want to save in value
>
> expireTime that is expireTime that the key not value after that
>
> this methode call async

void setAsyncInIfPresent(String key, Object o)
> key is the unique key in redis service
>
> o   is object that you want to save in value

< R > BaseDTO< R > fetch(String key, Boolean expireAfterFetch, Class< R > tClass)
> key              is the unique key in redis service
>
> expireAfterFetch if true , delete key after fetch
>
> tClass      :     the object you want to cast to it
>
> < R >              the type of class

BaseDTO< Object > fetch(String key, Boolean expireAfterFetch)
> key              is the unique key in redis service
>
> if expireAfterFetch set true , the methode delete key after fetch

BaseDTO< RedisResVM > fetchComplete(String key, Boolean expireAfterFetch)
>key              is the unique key in redis service
>
> if expireAfterFetch set true , the methode delete key after fetch

BaseDTO<Long> getExpireTime(String key, TimeUnit timeUnit)
>key      is the unique key in redis service
>
> timeUnit the type of time you want fetch expire time

BaseDTO<Boolean> delete(String key)
> key is the unique key in redis service
>
> return boolean for result
