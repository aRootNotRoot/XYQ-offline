package xyq.game.stage.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import xyq.game.XYQGame;
import xyq.game.stage.ChatOption;
import xyq.game.stage.LinkLabel;
import xyq.game.stage.WasActor;
import xyq.system.utils.RandomUT;

public class MessageHud  extends Group{
	final int MaxOpt=3;
	XYQGame game;
	public WasActor mainPanel;
	public WasActor headImg;
	Button clzBtn;
	public Label chatLabel;
	LinkLabel[] linkLabels;
	public MessageHud(XYQGame game){
		super();
		this.game=game;
		
		setSize(482, 167);
		setPosition(400, 226);
		mainPanel=new WasActor(game.rs,"wzife.wdf","UI\\通用对话框中");
		mainPanel.setPosition(0, 0);
		mainPanel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(mainPanel);
		
		clzBtn = new Button(game.rs.getClzBlueCommonButtonStyle());

		clzBtn.setPosition(460, 147);
		clzBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        addActor(clzBtn); 
        
        /*
        InfoActor actor=new InfoActor(game.rs);
        actor.setSize(160, 180);
        actor.setX(100);
        actor.setY(0);
        addActor(actor);
        */
        
        setVisible(false);
	}
	public String autoRowChangeInsert(String str){
		StringBuilder sb=new StringBuilder();
		int cc=0;
		for(int i=0;i<str.length();i++){
			sb.append(str.charAt(i));
			cc++;
			if(cc>=28){
				cc=0;
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	public void showChatDlg(String pack,String was,String chat){
		if(headImg!=null){
			headImg.remove();
			headImg=null;
		}
		if(linkLabels!=null){
			for(int i=0;i<linkLabels.length;i++){
				if(linkLabels[i]!=null)
					linkLabels[i].remove();
			}
			linkLabels=null;
		}
		if(!was.equals("")){
			headImg=new WasActor(game.rs,pack,was);
			headImg.setPosition(20, 167);
			headImg.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//右键关闭对话框
	            	if(button==1)
	            		setVisible(false);
	            	return false;
	            }
	        });
			addActor(headImg);
		}
		if(chatLabel!=null){
			chatLabel.remove();
			chatLabel=null;
		}
		String[] chats=chat.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		chatLabel = new Label(autoRowChangeInsert(chats[randomChat]), game.rs.getLabelStyle(chat));
		chatLabel.setBounds(15, 55, 430, 90);
		chatLabel.setAlignment(Align.topLeft);
		chatLabel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(chatLabel);
		setVisible(true);
	}
	public void showChatDlg(String pack,String was,String chatPack,String chatwas){
		if(headImg!=null){
			headImg.remove();
			headImg=null;
		}

		if(linkLabels!=null){
			for(int i=0;i<linkLabels.length;i++){
				linkLabels[i].remove();
			}
			linkLabels=null;
		}
		if(!was.equals("")){
			headImg=new WasActor(game.rs,pack,was);
			headImg.setPosition(20, 167);
			headImg.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//右键关闭对话框
	            	if(button==1)
	            		setVisible(false);
	            	return false;
	            }
	        });
			addActor(headImg);
		}
		if(chatLabel!=null){
			chatLabel.remove();
			chatLabel=null;
		}
		String chat=game.rs.getChatString(chatPack, chatwas);
		String[] chats=chat.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		chatLabel = new Label(autoRowChangeInsert(chats[randomChat]), game.rs.getLabelStyle(chat));
		chatLabel.setBounds(15, 55, 430, 90);
		chatLabel.setAlignment(Align.topLeft);
		chatLabel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(chatLabel);
		setVisible(true);
	}
	//TODO
	public void showOptionChatDlg(String pack,String was,String chat,ChatOption[] opts){
		if(headImg!=null){
			headImg.remove();
			headImg=null;
		}

		if(linkLabels!=null){
			for(int i=0;i<linkLabels.length;i++){
				if(linkLabels[i]!=null)
					linkLabels[i].remove();
			}
			linkLabels=null;
		}
		if(!was.equals("")){
			headImg=new WasActor(game.rs,pack,was);
			headImg.setPosition(20, 167);
			headImg.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//右键关闭对话框
	            	if(button==1)
	            		setVisible(false);
	            	return false;
	            }
	        });
			addActor(headImg);
		}
		if(chatLabel!=null){
			chatLabel.remove();
			chatLabel=null;
		}
		String[] chats=chat.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		chatLabel = new Label(autoRowChangeInsert(chats[randomChat]), game.rs.getLabelStyle(chat));
		chatLabel.setBounds(15, 55, 430, 90);
		chatLabel.setAlignment(Align.topLeft);
		chatLabel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(chatLabel);
		setVisible(true);
		if(opts!=null){
			int optLength=opts.length;
			boolean checkOK=true;
			if(optLength>0){
				for(int i=0;i<optLength;i++){
					if(opts[i]==null){
						Gdx.app.error("[ XYQ ]","[MessageHud] -> 聊天框->生成聊天对话框的时候失败了，原因是传入的选项处理器为null @ "+i);
						checkOK=false;
						break;
					}
				}
			}
			if(checkOK){
				if(linkLabels!=null){
					for(int j=0;j<linkLabels.length;j++){
						linkLabels[j].remove();
						linkLabels[j]=null;
					}
				}
				linkLabels=new LinkLabel[MaxOpt];
				for(int i=0;i<optLength&&i<MaxOpt;i++){
					linkLabels[i]=new LinkLabel(15, 53-20*i, opts[i].labelText, game.rs.getLabelStyle(opts[i].labelText), opts[i].clickAction);
					addActor(linkLabels[i]);
				}
			}
		}
	}
	public void showOptionChatDlg(String pack,String was,String chatPack,String chatwas,ChatOption[] opts){
		if(headImg!=null){
			headImg.remove();
			headImg=null;
		}

		if(linkLabels!=null){
			for(int i=0;i<linkLabels.length;i++){
				linkLabels[i].remove();
			}
			linkLabels=null;
		}
		if(!was.equals("")){
			headImg=new WasActor(game.rs,pack,was);
			headImg.setPosition(20, 167);
			headImg.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//右键关闭对话框
	            	if(button==1)
	            		setVisible(false);
	            	return false;
	            }
	        });
			addActor(headImg);
		}
		if(chatLabel!=null){
			chatLabel.remove();
			chatLabel=null;
		}
		String chat=game.rs.getChatString(chatPack, chatwas);
		String[] chats=chat.split("P N");
		int randomChat=RandomUT.getRandom(0, chats.length-1);
		while("".equals(chats[randomChat]))//保证不让其选到空的话
			randomChat=RandomUT.getRandom(0, chats.length-1);
		chatLabel = new Label(autoRowChangeInsert(chats[randomChat]), game.rs.getLabelStyle(chat));
		chatLabel.setBounds(15, 55, 430, 90);
		chatLabel.setAlignment(Align.topLeft);
		chatLabel.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	//右键关闭对话框
            	if(button==1)
            		setVisible(false);
            	return false;
            }
        });
		addActor(chatLabel);
		setVisible(true);
		if(opts!=null){
			int optLength=opts.length;
			boolean checkOK=true;
			if(optLength>0){
				for(int i=0;i<optLength;i++){
					if(opts[i]==null){
						Gdx.app.error("[ XYQ ]","[MessageHud] -> 聊天框->生成聊天对话框的时候失败了，原因是传入的选项处理器为null @ "+i);
						checkOK=false;
						break;
					}
				}
			}
			if(checkOK){
				if(linkLabels!=null){
					for(int j=0;j<linkLabels.length;j++){
						linkLabels[j].remove();
						linkLabels[j]=null;
					}
				}
				linkLabels=new LinkLabel[MaxOpt];
				for(int i=0;i<optLength&&i<MaxOpt;i++){
					linkLabels[i]=new LinkLabel(15, 53-20*i, opts[i].labelText, game.rs.getLabelStyle(opts[i].labelText), opts[i].clickAction);
					addActor(linkLabels[i]);
				}
			}
		}
	}
	public void hide(){
		setVisible(false);
	}
}
