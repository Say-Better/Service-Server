package com.saybetter.domain.solution.application.converter;

import com.saybetter.domain.member.domain.Member;
import com.saybetter.domain.solution.domain.Solution;
import com.saybetter.domain.solution.ui.dto.SolutionRequest;

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
