package com.saybetter.client.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.saybetter.client.api.dto.RecommendResponse;
import com.saybetter.client.api.dto.RecommendResult;

public class RecommendResultConverter {

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
