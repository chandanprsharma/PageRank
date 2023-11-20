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
public class MemberWithWeight implements Comparable<MemberWithWeight> {

	private int id;
	private float weight;

	@Override
	public int compareTo(MemberWithWeight m) {
		if(this.getWeight() > m.getWeight())
			return 1;
		else if(this.getWeight() < m.getWeight())
			return -1;
		else
			return 0;
	}

}
