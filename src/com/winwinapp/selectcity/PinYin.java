package com.winwinapp.selectcity;

import java.util.ArrayList;

import com.winwinapp.selectcity.HanziToPinyin3.Token;

public class PinYin {

	// ���ַ���ƴ������ĸԭ�����أ���ת��ΪСд
		public static String getPinYin(String input)
		{
			ArrayList<Token> tokens = HanziToPinyin3.getInstance().get(input);
			StringBuilder sb = new StringBuilder();
			if (tokens != null && tokens.size() > 0)
			{
				for (Token token : tokens)
				{
					if (Token.PINYIN == token.type)
					{
						sb.append(token.target);
					} else
					{
						sb.append(token.source);
					}
				}
			}
			return sb.toString().toLowerCase();
		}
}
