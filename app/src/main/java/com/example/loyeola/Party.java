package com.example.loyeola;

public class Party {
    private int id;
    private int raid_id;
    private String title;
    private String content;
    private int ess_supporter;
    private int supporters;
    private int dealers;
    private String discord_url;
    private String kakao_url;
    private String leader_id;

    public Party() {
    }

    public Party(int id, int raid_id, String title, String content, int ess_supporter, int supporters, int dealers, String discord_url, String kakao_url, String leader_id) {
        this.id = id;
        this.raid_id = raid_id;
        this.title = title;
        this.content = content;
        this.ess_supporter = ess_supporter;
        this.supporters = supporters;
        this.dealers = dealers;
        this.discord_url = discord_url;
        this.kakao_url = kakao_url;
        this.leader_id = leader_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRaid_id() {
        return raid_id;
    }

    public void setRaid_id(int raid_id) {
        this.raid_id = raid_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEss_supporter() {
        return ess_supporter;
    }

    public void setEss_supporter(int ess_supporter) {
        this.ess_supporter = ess_supporter;
    }

    public int getSupporters() {
        return supporters;
    }

    public void setSupporters(int supporters) {
        this.supporters = supporters;
    }

    public int getDealers() {
        return dealers;
    }

    public void setDealers(int dealers) {
        this.dealers = dealers;
    }

    public String getDiscord_url() {
        return discord_url;
    }

    public void setDiscord_url(String discord_url) {
        this.discord_url = discord_url;
    }

    public String getKakao_url() {
        return kakao_url;
    }

    public void setKakao_url(String kakao_url) {
        this.kakao_url = kakao_url;
    }

    public String getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(String leader_id) {
        this.leader_id = leader_id;
    }
}
