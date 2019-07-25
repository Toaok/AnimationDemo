package indi.toaok.animationdemo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        test();
    }

    public void test() {
        String str = "SUN公司被Oracle收购，是否意味着javad121被逼上了死路？";
        System.out.println();
        subString(str, getEnglishOrIntegerString(str));
    }


    private void subString(String str, String[] initStrings) {

        for (String sub : initStrings) {
            if (str.equals(sub)) {
                System.out.println(sub);
                break;
            }
            String[] childStrings = str.split(sub);
            for (String sub1 : childStrings) {
                if (sub1.equals("")) {
                    System.out.println(sub);
                    continue;
                }
                subString(sub1, initStrings);
            }
        }

    }


    /**
     * 截取字符串中的字符串数组
     *
     * @param str
     * @return
     */
    private String[] getEnglishOrIntegerString(String str) {
        String regex = "\\d+.\\d+|\\w+";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(regex);
        Matcher ma = pattern.matcher(str);
        while (ma.find()) {
            sb.append(ma.group());
            sb.append(",");
        }
        return sb.toString().split(",");
    }
}