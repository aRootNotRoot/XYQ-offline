package xyq.system.assets;

/**
 * 精灵染色方案
 * @author wpaul
 */
public class ColorationScheme {

	private int startPos;  //染色区段起始位置
	
	private int endPos;    //染色区段结束位置
	
    private short[][] colors;

    public ColorationScheme(int startPos,int endPos,short[][] colors) {
    	this.startPos = startPos;
    	this.endPos = endPos;
    	this.colors = colors;
    }

/*
          假设用于变换调色板的参数矩阵�?
    C11 C12 C13 
    C21 C22 C23 
    C31 C32 C33
           颜色混合公式
    R2=R*C11+G*C12+B*C13 
    G2=R*C21+G*C22+B*C23 
    B2=R*C31+G*C32+B*C33
    R2=R2>>8 
    G2=G2>>8 
    B2=B2>>8
*/

    public byte[] mix(byte r,byte g,byte b) {
        int r2 = ((r * colors[0][0] + g * colors[0][1] + b * colors[0][2]) >>> 8) ;
        int g2 = ((r * colors[1][0] + g * colors[1][1] + b * colors[1][2]) >>> 8);
        int b2 = ((r * colors[2][0] + g * colors[2][1] + b * colors[2][2]) >>> 8) ;
        r2 = Math.min(r2, 0x1f);
        g2 = Math.min(g2, 0x3f);
        b2 = Math.min(b2, 0x1f);
        byte[] comps = new byte[3];
        comps[0] = (byte) r2;
        comps[1] = (byte) g2;
        comps[2] = (byte) b2;
        return comps;
    }

    public short mix(short color) {
        byte[] rgbs = new byte[3];
        //red
        byte r = (byte) (((color >>> 11) & 0x1F));
        //green
        byte g = (byte) (((color >>> 5) & 0x3f));
        //blue
        byte b = (byte) ((color & 0x1F));
        //mix
        rgbs = this.mix(r,g,b);
        color = (short) ((rgbs[0] << 11) | (rgbs[1] << 5) | rgbs[2]);
        return color;
    }

	public int getStartPos() {
		return startPos;
	}

	public int getEndPos() {
		return endPos;
	}

}
