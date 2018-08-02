package xyq.game.stage;

public class ChatOption {
	public String labelText;
	public LinkLabelClickAction clickAction;
	public ChatOption(String labelText,LinkLabelClickAction clickAction){
		this.labelText=labelText;
		this.clickAction=clickAction;
	}
}
