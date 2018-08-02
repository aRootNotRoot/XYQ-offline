package xyq.system.assets;

public class FrameDatas {
	public Frame[][] frames;
	public FrameDatas(Frame[][] fr){
		this.frames=fr;
	}
	public Frame getFrame(int direct,int frameIndex){
		return frames[direct][frameIndex];
	}
	public Frame[] getFrames(int direct){
		return frames[direct];
	}
}
