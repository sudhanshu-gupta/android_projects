package com.amigo.rssreader;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 17/12/15.
 */
public class RSSParser extends DefaultHandler {

    private static String TAG = "RSSParser";
    private boolean insideItem = false;
    StringBuffer characterBuffer;
    public ArrayList<FeedItem> items;
    private FeedItem itemObject;

    public RSSParser() {
        items = new ArrayList<>();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.i(TAG, "Started Parsing");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i(TAG, "End Parsing");

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(localName.equalsIgnoreCase("item")) {
            insideItem = true;
            itemObject = new FeedItem();
        }
        if(insideItem) {
            if(localName.equalsIgnoreCase("title") || localName.equalsIgnoreCase("link") || localName.equalsIgnoreCase("description") || localName.equalsIgnoreCase("pubDate")) {
                characterBuffer = new StringBuffer();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equalsIgnoreCase("item")) {
            insideItem = false;
            items.add(itemObject);
        }
        if(insideItem) {
            if(localName.equalsIgnoreCase("title")) {
                Log.i(TAG, "title " + characterBuffer.toString());
                itemObject.setTitle(characterBuffer.toString());
                characterBuffer = null;
            } else if(localName.equalsIgnoreCase("link")) {
                Log.i(TAG, "link " + characterBuffer.toString());
                itemObject.setLink(characterBuffer.toString());
                characterBuffer = null;
            } else if(localName.equalsIgnoreCase("pubDate")) {
                Log.i(TAG, "Date " + characterBuffer.toString());
                itemObject.setPublicationDate(characterBuffer.toString());
                characterBuffer = null;
            } else if(localName.equalsIgnoreCase("description")) {
                Log.i(TAG, "Description " + characterBuffer.toString());
                itemObject.setDescription(characterBuffer.toString());
                characterBuffer = null;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(characterBuffer != null) {
            characterBuffer.append(ch, start, length);
        }
    }
}
