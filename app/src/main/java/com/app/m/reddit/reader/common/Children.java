package com.app.m.reddit.reader.common;

public class Children {
	private String kind;
	private Data data;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data {
		private String title, domain, subreddit, url,
				author, thumbnail, created_utc;
		private int num_comments, score, ups;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String getSubreddit() {
			return subreddit;
		}

		public void setSubreddit(String subreddit) {
			this.subreddit = subreddit;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		public String getCreated_utc() {
			return created_utc;
		}

		public void setCreated_utc(String created_utc) {
			this.created_utc = created_utc;
		}

		public int getNum_comments() {
			return num_comments;
		}

		public void setNum_comments(int num_comments) {
			this.num_comments = num_comments;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getUps() {
			return ups;
		}

		public void setUps(int ups) {
			this.ups = ups;
		}
	}
}
