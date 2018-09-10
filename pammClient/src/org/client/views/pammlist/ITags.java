package org.client.views.pammlist;

public interface ITags {
	public PammListData getDataByTags(String tags[]);
	public PammListData getDataByTags(int tags[]);
	public boolean pushTagFromRecord(String tag, int idRecord);
	public boolean pushTagFromRecord(int tag, int idRecord);
	public void addTagToRecord(int idRecord, String tag);
}
