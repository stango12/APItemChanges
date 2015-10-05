package apItemChanges;

import java.util.ArrayList;
import java.util.List;

import constant.Region;
import constant.staticdata.ItemData;
import dto.Match.MatchDetail;
import dto.Match.Participant;
import dto.Match.ParticipantStats;
import dto.Static.Item;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

public class Test {

	public static void main(String[] args) throws RiotApiException {
		RiotApi api = new RiotApi("API KEY", Region.NA);
		MatchDetail before = api.getMatch(1852562088);
		List<Participant> mids = getMidParticipants(before.getParticipants(), api);
		for(int i = 0; i < mids.size(); i++)
		{
			System.out.println(getChampName(mids.get(i), api));
			System.out.println("------------------------------------");
			ParticipantStats player = mids.get(i).getStats();
			List<Item> items = getOldItems(player, api);
			for(int j = 0; j < items.size(); j++)
			{
				System.out.println(items.get(j).getName());
			}
			System.out.println();
		}
	}

	/**
	 * Gets the participants of the people who played mid
	 * @param p List of participants
	 * @param api Riot API Code
	 * @return ArrayList of participants
	 * @throws RiotApiException
	 */
	public static List<Participant> getMidParticipants(List<Participant> p, RiotApi api) throws RiotApiException
	{
		List<Participant> mids = new ArrayList<Participant>();
		for(int i = 0; i < p.size(); i++)
		{
			if(p.get(i).getTimeline().getLane().equals("MID") ||p.get(i).getTimeline().getLane().equals("MIDDLE"))
			{
				mids.add(p.get(i));
			}
		}
		
		return mids;
	}
	
	/**
	 * Gets the champion name that the participant played
	 * @param p Participant
	 * @param api Riot API
	 * @return Champion Name in String
	 * @throws RiotApiException
	 */
	public static String getChampName(Participant p, RiotApi api) throws RiotApiException
	{
		return api.getDataChampion(p.getChampionId()).getName();
	}
	
	/**
	 * Gets the items the participant purchased in version 5.11
	 * @param p ParticipantStats
	 * @param api Riot API
	 * @return Items purchased in list<Item>
	 * @throws RiotApiException
	 */
	public static List<Item> getOldItems(ParticipantStats p, RiotApi api) throws RiotApiException
	{
		List<Item> items = new ArrayList<Item>();
		items.add(api.getDataItem((int) p.getItem0(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem1(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem2(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem3(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem4(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem5(), "en_US", "5.11.1", ItemData.TAGS));
		items.add(api.getDataItem((int) p.getItem6(), "en_US", "5.11.1", ItemData.TAGS));
		return items;
		/*
		 * I want to use 
		 * items.add(api.getDataItem((int) p.getItem0()));
		 * to get the items but because riot changed the item ids for boots sometime from patch 5.11 to 5.14,
		 * I have to use the longer dataItem method that asks to specify locale, version, and itemListData
		 */
	}
}
