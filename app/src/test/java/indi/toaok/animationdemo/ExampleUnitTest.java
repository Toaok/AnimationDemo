package indi.toaok.animationdemo;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static CharSequence sText = "123.121dsa fsd这是一个针对技术开Android 发者的一个应3asas用，你可以在掘金上获取";
    private static String sRegex = "[\\x00-\\xff]+";

    @Test
    public void addition_isCorrect() {
        test();
    }

    public void test() {
        CharSequence str = sText;
        System.out.println(Arrays.asList(getStrings(str.toString(), sRegex)));
        System.out.println(isMatcher('中',sRegex));

        System.out.println(Integer.MAX_VALUE);
    }

    public boolean isMatcher(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public boolean isMatcher(char str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.valueOf(str));
        return matcher.find();
    }

    private String[] getStrings(String str, String regex) {

        String[] result;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String[] matchers = getMatcher(matcher);
        String[] strings = str.split(regex);

        result = new String[(matchers == null ? 0 : matchers.length )+ (strings == null ? 0 : strings.length)];
        for (int i = 0; i < result.length; i++) {//
            if (strings[0] == null || strings[0].equals("")) {
                if (i % 2 == 0) {
                    if (i / 2 < matchers.length) {
                        result[i] = matchers[i / 2];
                    }
                } else {
                    if (i / 2 + 1 < strings.length) {
                        result[i] = strings[i / 2 + 1];
                    }
                }
            } else {
                if (i % 2 == 0) {
                    if (i / 2 < strings.length) {
                        result[i] = strings[i / 2];
                    }
                } else {
                    if (i / 2 < matchers.length) {
                        result[i] = matchers[i / 2];
                    }
                }
            }
        }
        return result;
    }

    private String[] getMatcher(Matcher matcher) {
        StringBuffer sb = new StringBuffer();
        boolean isFind = matcher.find();
        while (true) {
            if (!isFind) {
                break;
            }
            sb.append(matcher.group());
            isFind = matcher.find();
            if (isFind) {
                sb.append(",");
            }
        }
        return sb.toString() == null || sb.toString().equals("") ? null : sb.toString().split(",");
    }

}