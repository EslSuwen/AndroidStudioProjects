package com.example.testpic;

import java.util.List;


public class StrKit {
	//list转String
	 public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }
	
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}
	

	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}
	

	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim())|| "null".equals(str.trim()) ? false : true;
	}
	
	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}
	
	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}
	
	public static String ToDBC(String input) {
		   char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);
		}

    public static String formateRate(String rateStr){  
    
         if(rateStr.indexOf(".") != -1){  
             //获取小数点的位置
             int num = 0;
             num = rateStr.indexOf(".");  

             String dianAfter = rateStr.substring(0,num+1);  
             String afterData = rateStr.replace(dianAfter, "");  
             if(afterData.length() < 6){  
                afterData = afterData + "000000" ;  
             }else{  
                 afterData = afterData;  
             }  
             return rateStr.substring(0,num) + "." + afterData.substring(0,6);  
          }else{  
            if(rateStr == "1"){  
               return "100";  
            }else{  
                return rateStr;  
            }  
          }  
    }  
   
      
}




