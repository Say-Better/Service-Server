package io.say.better.domain.member.application.impl

import io.say.better.domain.member.application.converter.ConnectConverter
import io.say.better.storage.mysql.dao.repository.ConnectReadRepository
import io.say.better.storage.mysql.dao.repository.ConnectWriteRepository
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class ConnectService(
    private val connectReadRepository: ConnectReadRepository,
    private val connectWriteRepository: ConnectWriteRepository
) {

    fun connect(educator: Educator, learner: Learner) {
        val newConnect = ConnectConverter.toConnect(educator, learner)
        connectWriteRepository!!.save(newConnect)
    }
}
