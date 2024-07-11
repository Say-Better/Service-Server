package io.say.better.domain.member.application.impl

import io.say.better.domain.member.application.converter.ConnectConverter
import io.say.better.storage.mysql.domains.account.repository.ConnectReadRepository
import io.say.better.storage.mysql.domains.account.repository.ConnectWriteRepository
import io.say.better.storage.mysql.domains.account.entity.Educator
import io.say.better.storage.mysql.domains.account.entity.Learner
import org.springframework.stereotype.Service

@Service
class ConnectService(
    private val connectReadRepository: ConnectReadRepository,
    private val connectWriteRepository: ConnectWriteRepository,
) {
    fun connect(
        educator: Educator,
        learner: Learner,
    ) {
        val newConnect = ConnectConverter.toConnect(educator, learner)
        connectWriteRepository.save(newConnect)
    }
}
