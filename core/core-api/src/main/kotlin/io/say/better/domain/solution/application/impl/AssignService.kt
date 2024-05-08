package io.say.better.domain.solution.application.impl

import io.say.better.storage.mysql.dao.repository.ProgressReadRepository
import io.say.better.storage.mysql.dao.repository.ProgressWriteRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class AssignService(
    private val progressReadRepository: ProgressReadRepository,
    private val progressWriteRepository: ProgressWriteRepository
) {

}
