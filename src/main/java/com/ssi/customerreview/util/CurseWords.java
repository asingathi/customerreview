package com.ssi.customerreview.util;

import java.util.HashMap;
import java.util.Map;

public class CurseWords {
	
	private static Map<String,String> curseWords = null;
	
	public static Map<String,String> getCurseWords() {
		if( curseWords == null ) {
			curseWords = new HashMap<String,String>();
			curseWords.put( "ship", "ship" );
			curseWords.put( "miss", "miss" );
			curseWords.put( "duck", "duck" );
			curseWords.put( "punt", "punt" );
			curseWords.put( "rooster", "rooster" );
			curseWords.put( "mother", "mother" );
			curseWords.put( "bits", "bits" );
		}
		return curseWords;
	}
	
}
