package com.example.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    public static void main(String[] args) {
        String org="          \"id\": 3,\n" +
                "          \"product_id\": 511,\n" +
                "          \"title\": \"\\u780d\\u5230\\u4f60\\u5fc3\\u614c\",\n" +
                "          \"price\": \"100.00\",\n" +
                "          \"min_price\": \"0.00\",\n" +
                "          \"stop_time\": 1564502400,\n" +
                "          \"image\": \"http:\\/\\/wine.jiudicar.com\\/public\\/uploads\\/attach\\/2019\\/07\\/10\\/5d25955eebd92.jpg\"";
        //parm参数
        Pattern pattern=null;
        Matcher matcher=null;
        pattern=Pattern.compile("public String(.*?);");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("statAll."+tmp1+"=statComp."+tmp1+";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pattern=Pattern.compile("TextView(.*?);");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println(""+tmp1+".setText(bean."+tmp1+");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("-------------隔断--------------");
         pattern=Pattern.compile("(.*):(.*)");
         matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                String textview=tmp1.split("\\.")[0];
                System.out.println("result.put(\""+tmp1+"\","+textview+".getText().toString());");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("-------------隔断--------------");
        //

         pattern=Pattern.compile("(.*?):(.*)");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("public String "+tmp1+";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pattern=Pattern.compile("(.*?):(.*)");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("bean."+tmp1+"=jsonObject.optString(\""+tmp1+"\");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        pattern=Pattern.compile("(.*?):(.*)");
//        matcher=pattern.matcher(org);
//        while (matcher.find()){
//            try {
//                String tmp1=matcher.group(1).replace("\"","").trim();
//                System.out.println("bean."+tmp1+"=jsonObject.optString(\""+tmp1+"\");");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("-------------隔断--------------");
        pattern=Pattern.compile("(.*?)\n");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("map.put(\""+tmp1+"\", \"\");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
