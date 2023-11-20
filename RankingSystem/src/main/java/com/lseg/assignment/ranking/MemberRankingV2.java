package com.lseg.assignment.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.lseg.assignment.ranking.model.Member;

public class MemberRankingV2 {

	public static void main(String[] args) {
		String data = "[{\"voted_member\": 1,\"points_from_other_members\": [0,5,4,3,1]},{\"voted_member\": 2,\"points_from_other_members\": [5,0,5,5,5]},{\"voted_member\": 3,\"points_from_other_members\": [4,0,0,0,0]},{\"voted_member\": 4,\"points_from_other_members\": [1,0,0,0,0]},{\"voted_member\": 5,\"points_from_other_members\": [2,2,2,2,0]}]";
		JSONParser parser = new JSONParser();
		try {
			JSONArray votesArr = (JSONArray) parser.parse(data);
			List<Member> memberList = computeRank(votesArr);
			List<Integer> rankList = memberList.stream().sorted((m1, m2) -> m2.compareTo(m1))
					.collect(Collectors.toList()).stream().map(m -> m.getId()).collect(Collectors.toList());
			System.out.println(rankList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static List<Member> computeRank(JSONArray votesArr) {
		List<Member> memberList = new ArrayList<Member>();
		for (int i = 0; i < votesArr.size(); i++) {
			JSONObject record = (JSONObject) votesArr.get(i);
			Member member = new Member();
			member.setId(((Long) record.get("voted_member")).intValue());
			JSONArray votes = (JSONArray) record.get("points_from_other_members");
			for (int j = 0; j < votes.size(); j++) {
				member.setVotesCount(member.getVotesCount() + ((Long) votes.get(j)).intValue());
			}
			memberList.add(member);
		}
		return memberList;
	}

}
