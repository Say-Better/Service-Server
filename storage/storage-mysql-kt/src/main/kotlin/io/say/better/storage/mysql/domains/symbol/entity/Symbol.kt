package io.say.better.storage.mysql.domains.symbol.entity

import io.say.better.storage.mysql.domains.symbol.type.SymbolType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

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
    private val type: SymbolType,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val that = other as Symbol
        return title == that.title && imgUrl == that.imgUrl && type == that.type
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + imgUrl.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
