package xyq.system.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


/**文字工具类*/
public class TextUtil {
	public static void main(String[] args) {
		//TextUtil instance = new TextUtil();
		//String s = instance.getBytes("ē");
		//String s2 = instance.getCodes(instance.getBytes("a"));
		// String s3 = instance.getCodes(instance.getBytes("ē"));
		//String s4 = instance.getCodes(instance.getBytes("中"));
		System.out.println("Unicode:" + Unicode("两个"));
		// System.out.println("line:"+);
		System.out.println("org:你离建邺渔翁太远啦");
		System.out.println("new:" + clearColorCode("你离建邺渔翁太远啦"));
		System.out.println("length:" + getCStringLength("你离建邺渔翁太远啦",16));
		
		autoMutiLineDealN("人之初，性本善。性相近，习相远。苟不教，性乃迁。教之道，贵以专。昔孟母，择邻处。子不学，断机杼。",12,120,true);
		//System.out.println(makeAllWords());
		showAllChineseWords();
	}
	
	public static String shortSizeChar="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -#=+_!！[]{}.,|/";
	
	String getCodes(String a) {
		String s = getBytes(a);
		StringBuilder ans = new StringBuilder();
		if (s.length() == 4) {

			if (s.charAt(3) == '0') {
				ans.append(s.charAt(1));
				ans.append(s.charAt(2));
				ans.append('0');
				ans.append('0');
			} else {
				ans.append('0');
				ans.append(s.charAt(1));
				ans.append(s.charAt(2));
				ans.append(s.charAt(3));
			}
		} else {
			ans.append("EROR");
			return s;
		}
		return ans.toString();
	}

	String getBytes(String s) {
		try {
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2) {
				out.append(" ");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					out.append("00");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				out.append(str);
				out.append(str1);
			}
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getWeek(Date date){
		String[] weeks = {"日","一","二","三","四","五","六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index<0){
			week_index = 0;
		} 
		return weeks[week_index];
	}
	/**
	 * 将字符串转成unicode
	 * 
	 * @param str 待转字符串
	 * @return unicode字符串
	 */
	public static String Unicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			//sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}
	/**获取聊天文本的所有特殊代码（颜色代码和表情代码）
	 * @param stringm 待分析的聊天文本
	 * @return  ArrayList<String> 代码列表，如“#R”“#101”
	 * */
	public static ArrayList<String> getspecialCode(String stringm){
		ArrayList<String> list=new ArrayList<String>();
		Pattern pattern=Pattern.compile("#([RBGDWYPn]{1}|[1][1][0-3]|[1][0][0-9]|[1-9][0]|[1-9]{2}|[0-9]{1})");
		Matcher matcher=pattern.matcher(stringm);
		while(matcher.find()){
			list.add(matcher.group(0));
		}
		return list;
	}
	/**剔除聊天文本的所有特殊代码（颜色代码和表情代码）
	 * @param stringm 待剔除的聊天文本
	 * */
	public static String clearSpecialCode(String stringm){
		String temp=stringm;
		Pattern pattern=Pattern.compile("#([RBGDWYPn]{1}|[1][1][0-3]|[1][0][0-9]|[1-9][0]|[1-9]{2}|[0-9]{1})");
		Matcher matcher=pattern.matcher(stringm);
		while(matcher.find()){
			temp=temp.replaceFirst(matcher.group(0), "");
		}
		return temp;
	}
	/**获取一个单行字符串的长度*/
	public static int getStrLength(String str,int fontSize){
		int length=0;
		//FreeTypeFontGenerator.DEFAULT_CHARS
		for(int i=0;i<str.length();i++){
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(str.charAt(i))!=-1)
				length+=fontSize/2;
			else
				length+=fontSize;
		}
		return length;
	}
	/**根据指定的宽度width，自动将一个字符串填充换行符，使其成为多行字符*/
	public static String autoMultiLine(String text,int fontSize,int width){
		StringBuilder sBuilder=new StringBuilder();
		int length=0;
		//FreeTypeFontGenerator.DEFAULT_CHARS
		for(int i=0;i<text.length();i++){
			sBuilder.append(text.charAt(i));
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(text.charAt(i))!=-1)
				length+=fontSize/2;
			else
				length+=fontSize;
			
			if(length>width){
				length=0;
				sBuilder.append("\n");
			}
		}
		return sBuilder.toString();
	}
	/**剔除聊天文本的所有颜色代码
	 * @param stringm 待剔除的聊天文本
	 * */
	public static String clearColorCode(String stringm){
		String temp=stringm;
		Pattern pattern=Pattern.compile("#[RBGDWYPn]{1}");
		Matcher matcher=pattern.matcher(stringm);
		while(matcher.find()){
			temp=temp.replaceFirst(matcher.group(0), "");
		}
		return temp;
	}
	/***
	 * 获取文本的绘制长度，剔除颜色代码后的绘制长度
	 * */
	public static int getCStringLength(String text, int Fontsize) {
		int length = 0;
		
		text=clearColorCode(text);
		char lastChar = 0;
		for(int i=0,size=text.length();i<size;i++){
			if(shortSizeChar.indexOf(text.charAt(i))!=-1&&shortSizeChar.indexOf(lastChar)!=-1)
			{
				//如果遇到的字是短字，且上一个字也是短字
				length+=Fontsize/1.8;
			}
			else if(shortSizeChar.indexOf(text.charAt(i))!=-1&&shortSizeChar.indexOf(lastChar)==-1)
			{
				//如果遇到的字是短字，且上一个字不是短字
				length+=Fontsize;
				length+=2;
			}
			else if(shortSizeChar.indexOf(text.charAt(i))==-1&&shortSizeChar.indexOf(lastChar)!=-1)
			{
				//如果遇到的字不是短字，且上一个字是短字
				length+=Fontsize;
				length-=Fontsize/1.8;
				length+=4;
			}else
			{
				length+=Fontsize;
			}
			lastChar=text.charAt(i);
		}
		
		return length;
	}

	public static ArrayList<String> autoMutiLine(String text,int width) {
		ArrayList<String> lines=new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		int widthCount = 0;
		for(int i=0;i<text.length();i++){
			String st=String.valueOf(text.charAt(i));
			sb.append(st);
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(st)!=-1){
				widthCount+=8;
			}else{
				widthCount+=16;
			}
			
			if(widthCount+16>width){
				widthCount=0;
				lines.add(sb.toString());
				sb.setLength(0);
			}
		}
		
		
		return lines;
	}
	public static ArrayList<String> autoMutiLine(String text,int fontSize,int width) {
		ArrayList<String> lines=new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		int widthCount = 0;
		
		for(int i=0;i<text.length();i++){
			String st=String.valueOf(text.charAt(i));
			
			sb.append(st);
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(st)!=-1){
				widthCount+=fontSize/2;
			}else{
				widthCount+=fontSize;
			}
			
			if(widthCount+fontSize>width){
				widthCount=0;
				lines.add(sb.toString());
				sb.setLength(0);
			}
			if(i>=text.length()-1){
				if(sb.length()!=0)
					lines.add(sb.toString());
			}
		}
		
		
		return lines;
	}
	public static float getMaxStrWidth(String words, int fontSize, int maxWidth) {
		int widthCount = 0;
		
		for(int i=0;i<words.length();i++){
			String st=String.valueOf(words.charAt(i));
			
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(st)!=-1){
				widthCount+=fontSize/2;
			}else{
				widthCount+=fontSize;
			}
			
			if(widthCount>maxWidth){
				widthCount=maxWidth;
				break;
			}
		}
		
		return widthCount;
	}
	public static String makeAllWords(){
		StringBuilder sBuilder=new StringBuilder();
		for(int i=0;i<=65535;i++){
			sBuilder.append((char)i);
		}
		return sBuilder.toString();
	}
	public static String makeAllChineseWords(){
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append(FreeTypeFontGenerator.DEFAULT_CHARS);
		String start = "\\u4e00";
		String end = "\\u9fa5";
		int s = Integer.parseInt(start.substring(2, start.length()), 16);
		int e = Integer.parseInt(end.substring(2, end.length()), 16);
		for (int i = s; i <= e; i++) {
			sBuilder.append((char)i);
		}
		return sBuilder.toString();
	}
	public static String makeAllChineseWords(String begin,String end){
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append(FreeTypeFontGenerator.DEFAULT_CHARS);
	
		int s = Integer.parseInt(begin.substring(2, begin.length()), 16);
		int e = Integer.parseInt(end.substring(2, end.length()), 16);
		for (int i = s; i <= e; i++) {
			sBuilder.append((char)i);
		}
		return sBuilder.toString();
	}
	/*
	不啦不啦不啦不啦
	不啦不啦\n不啦不啦
	不啦不啦不啦不啦
	*/
	/**根据宽度自动拆分字符串为行字符串数组*/
	public static ArrayList<String> autoMutiLineDealN(String text,int fontSize,int width,boolean needClearEmpty) {
		ArrayList<String> lines=new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		int widthCount = 0;
		
		for(int i=0;i<text.length();i++){
			String st=String.valueOf(text.charAt(i));
			
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(st)!=-1){
				widthCount+=fontSize/2;
			}else{
				widthCount+=fontSize;
			}
			if(st.equals("\n")){
				widthCount=0;
				lines.add(sb.toString());
				sb.setLength(0);
			}else{
				sb.append(st);
				if(widthCount+fontSize>width){
					widthCount=0;		
					lines.add(sb.toString());
					sb.setLength(0);
				}
			}
			
			
			if(i>=text.length()-1){
				if(sb.length()!=0)
					lines.add(sb.toString());
			}
		}
		if(needClearEmpty){
			ArrayList<String> noEmpty=new ArrayList<String>();
			for(int i=0;i<lines.size();i++){
				if(!lines.get(i).equals("\n")&&!lines.get(i).equals(""))
					noEmpty.add(lines.get(i));
			}
			return noEmpty;
		}
		else{
			return lines;
		}
		
		
	}
	public static String autoMutiLine_Str(String text,int fontSize,int width) {

		StringBuilder sb=new StringBuilder();
		int widthCount = 0;
		
		for(int i=0;i<text.length();i++){
			String st=String.valueOf(text.charAt(i));
			
			sb.append(st);
			if(FreeTypeFontGenerator.DEFAULT_CHARS.indexOf(st)!=-1){
				widthCount+=fontSize/2;
			}else{
				widthCount+=fontSize;
			}
			
			if(widthCount+fontSize>width){
				widthCount=0;
				sb.append("\n");
			}
		}
		
		
		return sb.toString();
	}
	public static void showAllChineseWords(){
		String start = "\\u4e00";
		String end = "\\u9fa5";
		int s = Integer.parseInt(start.substring(2, start.length()), 16);
		int e = Integer.parseInt(end.substring(2, end.length()), 16);
		for (int i = s; i <= e; i++) {
		    System.out.println((char) i);
		}
	}
	public static String readTxt(String filePath)
    {
        // 读取txt内容为字符串
        StringBuffer txtContent = new StringBuffer();
        // 每次读取的byte数
        byte[] b = new byte[8 * 1024];
        InputStream in = null;
        try
        {
            // 文件输入流
            in = new FileInputStream(filePath);
            while (in.read(b) != -1)
            {
                // 字符串拼接
                txtContent.append(new String(b));
            }
            // 关闭流
            in.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return txtContent.toString();
    }

	
}
