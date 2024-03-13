package io.say.better.domain.solution.application.converter;

import io.say.better.storage.mysql.domain.entity.Member;
import io.say.better.storage.mysql.domain.entity.Solution;
import io.say.better.domain.solution.ui.dto.SolutionRequest;

public class SolutionConverter {

	private SolutionConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static Solution toSolution(SolutionRequest.CreateSolution request, Member member) {
		return Solution.builder()
				.writer(member)
				.title(request.getTitle())
				.baselineSessionTime(request.getBaselineSessionTime())
				.interCompleteThresh(request.getInterCompleteThresh())
				.interMaintainTerm(request.getInterMaintainTerm())
				.commOptTimes(request.getCommOptTimes())
				.commOptCnt(request.getCommOptCnt())
				.build();
	}

}
