package nexters.waterheart.dto;

public class Write {
	private int no;
	private String date;
	private String rewardText;
	private String rewardImage;
	
	public Write () {
		
	}
	public String getRewardText() {
		return rewardText;
	}
	public void setRewardText(String rewardText) {
		this.rewardText = rewardText;
	}
	public String getRewardImage() {
		return rewardImage;
	}
	public void setRewardImage(String rewardImage) {
		this.rewardImage = rewardImage;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
