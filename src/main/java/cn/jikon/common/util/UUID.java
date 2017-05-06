package cn.jikon.common.util;

import java.util.Date;
import java.util.Random;

/**
 * Created by lwj on 2017/4/11.
 */
public class UUID {

    protected static IdGenerator idGenerator = IdGenerator.getInstance();

    public static String getIdString() {
        return String.valueOf(idGenerator.generate());

    }


    public static String getBillCodeUUid(){
        return DateUtil.dateFomatYYMMDD(new Date()) + getStringRandom(4);
    }

    public static String getRandom(){
        return getStringRandom(6);
    }

    //生成随机数字和字母,
    private static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toUpperCase();
    }




}
