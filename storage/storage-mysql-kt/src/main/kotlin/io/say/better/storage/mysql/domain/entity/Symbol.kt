package io.say.better.storage.mysql.domain.entity

import io.say.better.storage.mysql.domain.constant.SymbolType
import jakarta.persistence.*


@Entity(name = "SYMBOL")
class Symbol(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "symbol_id")
        private val symbolId: Long,

        @Column(name = "title", nullable = false, length = 100) val title: String,

        @Column(name = "img_url", nullable = false) val imgUrl: String,

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false, length = 20)
        private val type: SymbolType
)
