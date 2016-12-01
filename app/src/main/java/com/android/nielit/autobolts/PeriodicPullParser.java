package com.android.nielit.autobolts;

import android.content.Context;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nijil on 30-11-2016.
 */

public class PeriodicPullParser {

    List<MDetails> mDetails;

    MDetails mDetail;
    String curText;

    public PeriodicPullParser(){
        mDetails = new ArrayList<MDetails>();
    }

    public List<MDetails> getMDetails() {
        return mDetails;
    }

    public List<MDetails> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try
        {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("Pulsar-200ns")) {
                            mDetail = new MDetails();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        curText = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("Pulsar-200ns")) {
                            mDetail.setS1(curText);
                            mDetails.add(mDetail);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return mDetails;
    }

    /*public static List<MDetails> getPeriodicDetails(Context context){
        List<MDetails> mDetails;
        MDetails curDetails = null;
        String curText = "";
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            FileInputStream fis = context.openFileInput(get"periodic.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagname = xpp.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase(KEY_V)){
                            curDetails= new MDetails();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(tagname.equalsIgnoreCase(KEY_V)){
                            mDetails.add(curDetails);
                        } else if(tagname.equalsIgnoreCase(KEY_S1)){
                            curDetails.setS1(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S2)){
                            curDetails.setS2(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S3)){
                            curDetails.setS3(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S4)){
                            curDetails.setS4(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S5)){
                            curDetails.setS5(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S6)){
                            curDetails.setS6(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S7)){
                            curDetails.setS7(curText);
                        } else if(tagname.equalsIgnoreCase(KEY_S8)){
                            curDetails.setS8(curText);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return mDetails;
    }*/
}
