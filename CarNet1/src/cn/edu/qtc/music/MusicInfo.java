package cn.edu.qtc.music;

public class MusicInfo {
	/**
	 * mp3Info.setId(id); mp3Info.setTitle(title); mp3Info.setArtist(artist);
	 * mp3Info.setDuration(duration); mp3Info.setSize(size);
	 * mp3Info.setUrl(url); mp3Infos.add(mp3Info);
	 */
	private long id;
	private String title;
	private long duration;
	private long size;
	private String artist;
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "MusicInfo [id=" + id + ", title=" + title + ", duration="
				+ duration + ", size=" + size + ", artist=" + artist + ", url="
				+ url + "]";
	}

}
