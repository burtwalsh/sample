package jp.mwsoft.sample.lucene.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKWidthFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class CJKWidthFilterSample {

    public static void main(String[] args) throws Exception {

        MyAnalyzer analyzer = new MyAnalyzer();

        String str = "１２３ ＡＢＣ ﾊﾝｶｸｶﾅ";

        Reader reader = new StringReader(str);
        TokenStream stream = analyzer.tokenStream("", reader);

        while (stream.incrementToken()) {
            CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
            System.out.print(term.toString() + "\t");
        }
        //=>   123	ABC	ハンカクカナ	 
    }

    static class MyAnalyzer extends Analyzer {
        public final TokenStream tokenStream(String fieldName, Reader reader) {
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, reader);
            result = new CJKWidthFilter(result);
            return result;
        }
    }
}
