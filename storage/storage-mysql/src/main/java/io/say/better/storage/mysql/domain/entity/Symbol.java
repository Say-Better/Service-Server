package io.say.better.storage.mysql.domain.entity;

import io.say.better.storage.mysql.domain.constant.SymbolType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "SYMBOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Symbol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "symbol_id")
	private Long symbolId;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "img_url", nullable = false)
	private String imgUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private SymbolType type;

}
