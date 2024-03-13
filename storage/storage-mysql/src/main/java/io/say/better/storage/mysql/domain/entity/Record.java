package io.say.better.storage.mysql.domain.entity;

import io.say.better.storage.mysql.domain.constant.ReactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "RECORD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Long recordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", nullable = false)
	private Review review;

	@Column(name = "order_num", nullable = false, columnDefinition = "int default 0")
	private Integer orderNum;

	@Enumerated(EnumType.STRING)
	@Column(name = "reaction_type", nullable = false, length = 20)
	private ReactionType reactionType;

}
