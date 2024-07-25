package io.say.better.storage.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisUtil(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    // key 에 해당하는 데이터 얻어오는 메소드
    fun getData(key: String): String? {
        val valueOperations = stringStringValueOperations
        return valueOperations[key]
    }

    // key - value 데이터 설정하는 메소드
    fun setData(
        key: String,
        value: String,
    ) {
        val valueOperations = stringStringValueOperations
        valueOperations[key] = value
    }

    // key 에 해당하는 데이터 삭제하는 메소드
    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }

    // key 에 해당하는 데이터 만료기간 설정 메소드
    fun setDataExpire(
        key: String,
        value: String,
        duration: Long?,
    ) {
        val valueOperations = stringStringValueOperations
        val expireDuration = Duration.ofSeconds(duration!!)
        valueOperations[key, value] = expireDuration
    }

    private val stringStringValueOperations: ValueOperations<String, String>
        get() = stringRedisTemplate.opsForValue()

    fun setConnectCode(
        code: String,
        email: String,
    ) {
        setDataExpire(code, email, CONNECT_CODE_EXPIRE)
    }

    companion object {
        private const val CONNECT_CODE_EXPIRE = 60 * 5L
    }
}
