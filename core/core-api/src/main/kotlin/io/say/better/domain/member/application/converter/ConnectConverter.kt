package io.say.better.domain.member.application.converter

import io.say.better.storage.mysql.domain.entity.Connect
import io.say.better.storage.mysql.domain.entity.Member

class ConnectConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toConnect(educator: Member?, learner: Member?): Connect {
            return Connect.builder()
                    .educator(educator)
                    .learner(learner)
                    .build()
        }
    }
}
