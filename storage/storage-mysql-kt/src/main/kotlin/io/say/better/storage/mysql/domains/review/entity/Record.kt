package io.say.better.storage.mysql.domains.review.entity

import io.say.better.storage.mysql.domains.review.type.ReactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "RECORD")
class Record(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "record_id")
    private var recordId: Long = 0,
    @JoinColumn(name = "review_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private val review: Review,
    @Column(name = "voice", nullable = true)
    private var voice: String?,
    @Column(name = "order_num", nullable = false, columnDefinition = "int default 0")
    private var orderNum: Int,
    @Enumerated(STRING)
    @Column(name = "reaction_type", length = 20)
    private var reactionType: ReactionType? = null,
) {
    constructor(review: Review, orderNum: Int) : this(
        review = review,
        orderNum = orderNum,
        voice = null,
        reactionType = null,
    )

    fun saveVoice(voice: String) {
        this.voice = voice
    }

    fun updateReactionType(reactionType: ReactionType) {
        this.reactionType = reactionType
    }
}
