package com.metadata.androidlibrary;
import android.content.res.XmlResourceParser;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Created by plee on 4/13/2017.
 */

public class FileProcessor {

    public FileProcessor(){}

    public ArrayList<Member> readMemberXML(InputStream input) throws ParserConfigurationException, IOException, SAXException {

        String memberID = "";
        String memberName = "";
        String password = "";
        ArrayList<Member> memberList = new ArrayList<Member>();
        InputStream inputStream = input;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        NodeList members = doc.getElementsByTagName("Member");
        //loop through each parent element
        for(int i = 0; i < members.getLength(); i++) {
            Node node = members.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element libItemElement = (Element) node;
                memberID = libItemElement.getAttribute("id");
                memberName = libItemElement.getAttribute("name");
                password = libItemElement.getAttribute("password");
            }
            memberList.add(new Member(memberName, memberID, password));
        }

        return memberList;
    }
}

