package com.lseg.assignment.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member implements Comparable<Member> {

	private int id;
	private int votesCount;

	@Override
	public int compareTo(Member m) {
		if(this.getVotesCount() > m.getVotesCount())
			return 1;
		else if(this.getVotesCount() < m.getVotesCount())
			return -1;
		else
			return 0;
	}

}
