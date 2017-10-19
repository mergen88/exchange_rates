package kz.mergen.exchagerates.exchangerates.Utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;

import kz.mergen.exchagerates.exchangerates.Models.RatesModel;

/**
 * Created by arman on 13.09.17.
 */

public class XmlParser {

    private StringBuffer datas;
    private static XmlParser instance;
    private XmlPullParser parser;
    public static XmlParser getInstance(){
        if(instance==null){
            return new XmlParser();
        }
        return instance;
    }

    public XmlParser parse(InputStream inputStream){
        try {
            parser = Xml.newPullParser();
            parser.setInput(inputStream,null);
        } catch (Exception e){
            Log.i("AAA", "xmlparsererror = "+e.getMessage());
        }
        return this;
    }
    public XmlParser findTag(String tagName, String[] elements){
        datas = new StringBuffer();
        try {
            while (parser.next()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG &&
                        parser.getName().equals(tagName)) {

                    while(parser.next()!=XmlPullParser.END_DOCUMENT){
                        for (String element : elements) {
                                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals(element)) {
                                    parser.next();
                                    datas.append(parser.getText());
                                    datas.append("%");
                                }

                            }
                    }

                }
            }
        } catch (Exception e) {
            Log.i("AAA","findTagError = "+e.getMessage());
        }
        return this;
    }
    public ArrayList<RatesModel> getRatesModels(){
        ArrayList<RatesModel> ratesModels = new ArrayList<>();
        int count = datas.toString().split("%").length;
        for(int i=0;i<count;i+=4){
            ratesModels.add(new RatesModel(datas.toString().split("%")[i+2].toString(),
                    datas.toString().split("%")[i].toString(),
                    datas.toString().split("%")[i+1].toString(),
                    datas.toString().split("%")[i+3].toString(),true));
        }
        return ratesModels;
    }
}
