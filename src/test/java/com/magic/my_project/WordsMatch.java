package com.magic.my_project;

import com.magic.my_project.words_match.StringMatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caodeju
 * @date 2020/9/11 下午4:24
 */
public class WordsMatch {

    @Test
    public void test_StringMatch() throws Exception {
        //String test = "我是中国人毛a泽a东款mz东家手机号毛家居服\n\t毛a泽东";
        String test = "a毛泽东a--a毛a泽东a--a毛泽a东a--a毛a泽a东a--a毛线团a--沼泽东--毛啊啊东";
        List<String> list = new ArrayList<String>();

        list.add("毛?泽.东");
        list.add("毛.泽?东");

        System.out.println("StringMatch run Test.");
        System.out.println(test);

        StringMatch iwords = new StringMatch();
        iwords.SetKeywords(list);

        String str = iwords.Replace(test, '*');
        System.out.println(str);
    }

}
