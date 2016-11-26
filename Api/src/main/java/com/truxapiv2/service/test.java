package com.truxapiv2.service;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class test {
   public static void main(String[] args){

      try {	
         File inputFile = new File("C:/Users/E6410/Downloads/sample_shipment_data.xml");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("HAWB");
         System.out.println("----------------------------");

     	for (int temp = 0; temp < nList.getLength(); temp++) {

     		Node nNode = nList.item(temp);
     				
     		System.out.println("\nCurrent Element :" + nNode.getNodeName());
     				
     		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

     			Element eElement = (Element) nNode;

     			System.out.println("HAWBNumber : " + eElement.getElementsByTagName("HAWBNumber").item(0).getTextContent());
     			System.out.println("OrderNumber : " + eElement.getElementsByTagName("OrderNumber").item(0).getTextContent());

     		}
     	}
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}