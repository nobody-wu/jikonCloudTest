package cn.jikon.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/4/6.
 */
public class DateUtil {

    public final static String formatYearMonthDay ="yyyy-MM-dd";

    public static String fomatDateToString(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat(formatYearMonthDay);
        String result = "";
        try{
            if(null != strDate && !"".equals(strDate)){
                result = sdf.format(sdf.parse(strDate));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String dateFomat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(formatYearMonthDay);
        return sdf.format(date);
    }

    public static String dateFomatYYMMDD(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(date);
    }

}
