package io.say.better.client.symbol.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.say.better.client.symbol.api.dto.RecommendResult;
import io.say.better.client.symbol.api.dto.RecommendResponse;

public class RecommendResultConverter {

	private RecommendResultConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static RecommendResult.SymbolRecommend toSymbolRecommend(RecommendResponse.SymbolRecommend recommend) {
		return RecommendResult.SymbolRecommend.builder()
				.symbols(getSymbols(recommend))
				.build();
	}

	private static List<String> getSymbols(RecommendResponse.SymbolRecommend recommend) {
		String symbolString = recommend.getSymbol();
		String[] symbols = symbolString.split(",");
		return new ArrayList<>(Arrays.asList(symbols));
	}

}
