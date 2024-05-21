package io.say.better.domain.member.application.converter

import io.say.better.storage.mysql.domain.entity.Connect
import io.say.better.storage.mysql.domain.entity.Educator
import io.say.better.storage.mysql.domain.entity.Learner

class ConnectConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toConnect(
            educator: Educator,
            learner: Learner,
        ): Connect {
            return Connect(
                educator = educator,
                learner = learner,
            )
        }
    }
}
