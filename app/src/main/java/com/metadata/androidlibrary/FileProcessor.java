package com.metadata.androidlibrary;
import android.content.res.XmlResourceParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.metadata.LibraryDomain.*;

/**
 * Created by plee on 4/13/2017.
 */

public class FileProcessor {

    private ArrayList<InventoryItem> libItem = new ArrayList<InventoryItem>();
    private ArrayList<InventoryItem> libItem2 = new ArrayList<InventoryItem>();
    private Library library = new Library();
    private MemberList memberList = new MemberList();
    private com.metadata.LibraryDomain.Member member;
    private File file;
    private JSONObject jsonObject;

    //static variables uses by JSON methods
    private enum jsonStrings {item_artist, item_author, library_items, item_name, item_id, item_type, CD, DVD, Book, Magazine, item_isCheckedOut, item_dueDate, item_checkoutDate, item_checkedOutTo}
    private static final String ITEM_ARTIST = "item_artist";
    private static final String ITEM_AUTHOR = "item_author";
    private static final String LIBRARY_ITEMS = "library_items";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_TYPE = "item_type";
    private static final String CD = "CD";
    private static final String DVD = "DVD";
    private static final String BOOK = "Book";
    private static final String MAGAZINE = "Magazine";

    //static variables used by XML methods
    private enum xmlStrings {Item, id, type, CD, DVD, MAGAZINE, BOOK, Name, Artist, Author, Volume, item_isCheckedOut, item_dueDate, item_checkoutDate, item_checkedOutTo}
    private static final String ITEM = "Item";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String XML_CD = "CD";
    private static final String XML_DVD = "DVD";
    private static final String XML_MAGAZINE = "MAGAZINE";
    private static final String XML_BOOK = "BOOK";
    private static final String NAME = "Name";
    private static final String ARTIST = "Artist";
    private static final String AUTHOR = "Author";
    private static final String VOLUME = "Volume";

    // static variables shared by XML and JSON file methods
    private static final String ITEM_ISCHECKEDOUT = "item_isCheckedOut";
    private static final String ITEM_DUEDATE = "item_dueDate";
    private static final String ITEM_CHECKOUTDATE = "item_checkoutDate";
    private static final String ITEM_CHECKEDOUTTO = "item_checkedOutTo";

    //variables
    String itemName = "";
    String itemID = "";
    String itemType = "";
    String artist = "";
    String author = "";
    String volume = "";
    String itemDueDate = null;
    String itemCheckOutDate = null;
    boolean isCheckedOut = false;
    String checkedOutTo = null;


    public FileProcessor(){}


    //read XML file for library items
    public ArrayList<InventoryItem> processXMLData(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        NodeList itemList = doc.getElementsByTagName("Item");
        //loop through each parent element
        for(int i = 0; i < itemList.getLength(); i++){
            Node node = itemList.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE){
                Element libItemElement = (Element) node;
                String itemID = libItemElement.getAttribute("id");
                String itemType = libItemElement.getAttribute("type");

                //get all child nodes
                NodeList libItemList = libItemElement .getChildNodes();

                //loop through all child nodes, defining variable where possible
                for(int j = 0; j< libItemList.getLength(); j++){
                    Node childNode = libItemList.item(j);
                    if(childNode.getNodeType()==Node.ELEMENT_NODE){
                        Element metadata = (Element) childNode;
                        switch(xmlStrings.valueOf(metadata.getTagName())){
                            case Artist: artist = metadata.getTextContent();
                                break;
                            case Author: author = metadata.getTextContent();
                                break;
                            case Volume: volume = metadata.getTextContent();
                                break;
                            case Name: itemName = metadata.getTextContent();
                                break;
                            case item_isCheckedOut: isCheckedOut = Boolean.parseBoolean(metadata.getTextContent());
                                break;
                            case item_dueDate: itemDueDate = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    itemDueDate = null;
                                }
                                break;
                            case item_checkoutDate: itemCheckOutDate = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    itemCheckOutDate = null;
                                }
                                break;
                            case item_checkedOutTo: checkedOutTo = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    checkedOutTo = null;
                                }
                                break;
                        }
                    }
                }

                if(itemType.equals(XML_CD)) {
                    libItem.add(new CD(itemID, itemName, itemType, artist, itemDueDate, itemCheckOutDate, checkedOutTo));
                }
                if(itemType.equals(XML_DVD)){
                    libItem.add(new DVD(itemID, itemName, itemType, itemDueDate, itemCheckOutDate, checkedOutTo));
                }
                if(itemType.equals(XML_BOOK)) {
                    libItem.add(new Book(itemID, itemName, itemType, author, itemDueDate, itemCheckOutDate, checkedOutTo));
                }
                if(itemType.equals(XML_MAGAZINE)) {
                    libItem.add(new Magazine(itemID, itemName, itemType, volume, itemDueDate, itemCheckOutDate, checkedOutTo));
                }
            }
        }
        return libItem;
    }

    //
    //read member xml file
    //put each member object in an arraylist
    //return arraylist of member elements
    //
    public ArrayList<Member> readMemberXML(InputStream input) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Member> memberList = new ArrayList<Member>();
        InputStream inputStream = input;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        NodeList members = doc.getElementsByTagName("Member");
        //loop through each parent element
        for(int i = 0; i < members.getLength(); i++) {

            String memberID = "";
            String memberName = "";
            String password = "";
            String privilege = "";

            Node node = members.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element memberElement = (Element) node;
                memberID = memberElement.getAttribute("id");
                memberName = memberElement.getAttribute("name");
                password = memberElement.getAttribute("password");
                privilege = memberElement.getAttribute("privilege");
            }
            memberList.add(new Member(memberName, memberID, password, privilege));

        }

        return memberList;
    }

    public ArrayList<InventoryItem> processJSONData(InputStream input) throws ParseException, FileNotFoundException, IOException {

        ArrayList<InventoryItem> itemList = new ArrayList<InventoryItem>();
        InputStream inputStream = input;
        return libItem2;
    }


    
}

