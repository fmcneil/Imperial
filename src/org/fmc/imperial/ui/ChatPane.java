package org.fmc.imperial.ui;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTML;
import java.lang.String;
import java.lang.StringBuffer;
import java.awt.Color;

public class ChatPane extends JTextPane {
 
 Board board;
 String fontSize = "H1";
 
 public ChatPane(Board b) {
  super();
  
  board = b;
  
  setEditable(false);
  setContentType("text/html");
  HTMLEditorKit kit = new HTMLEditorKit();
  StyleSheet css = new StyleSheet();
  css.addRule("BODY{ margin : 0;}");
  css.addRule("P{ margin : 0;}");
  css.addRule("A{ color:#0000FF; text-decoration:underline;}");
        kit.setStyleSheet(css);
   setEditorKit(kit);
  setBackground(new Color(249, 249, 250));
 }

 public void clear() { 
  setText("");
 }
 
 /**
  * Sends a text to the chat window. Parses the message to pick
  * out emoticons and links.
  * @param un the name of the user sending the message
  * @param message the message to be sent
  * @param whisper indicates the message was a wisper and makes the 
  *     message italic
  */
 public void sendText(String un, String message) {
  sendText(un,message,false);
 }
 
 /**
  * Sends a text to the chat window. Parses the message to pick
  * out emoticons and links.
  * @param un the name of the user sending the message
  * @param message the message to be sent
  */
 public void sendText(String un, String message, boolean whisper) {
  StringBuffer buff = new StringBuffer();
  
  HTMLDocument doc = (HTMLDocument) getDocument();
  HTMLEditorKit kit = (HTMLEditorKit) getEditorKit();

  /*
  if (un == null || client.getPlayer().getName() == null) {
   return;
  }
  */
  
  if (!"server".equals(un)) {
   message = message.replaceAll("<", "&lt;");
   message = message.replaceAll(">", "&gt;");
  }
  message = message.replaceAll("\n", "<br>");
  
  
//  if (client.getPlayer().getName().equals(un)) {
  if (false) {
   buff.append("<font color=#009900><b>");
  } else if (un.equals("server")) {
   buff.append("<font color=#990000><b>");
  } else if (un.equals("Manager")) {
   buff.append("<font color=#CC0000><b>");
  } else {
   buff.append("<font color=#000099><b>");
  }
  buff.append(un);
  buff.append("</b></font>");

  buff.append("&nbsp;&nbsp;:&nbsp;");
  if (whisper) {
   buff.append("<i>");
  }
  buff.append(message);
  if (whisper) {
   buff.append("</i>");
  }
  
  if (buff.length() > 0) {
   try {
    buff.append("<br>");
    kit.insertHTML(doc,doc.getLength(), buff.toString(), 0,0,HTML.Tag.FONT);
    setCaretPosition(doc.getLength());
   } catch (Throwable t) { t.printStackTrace(); }
  }
  
  /*
  if (message.startsWith("afk") || message.startsWith("brb")) {
   client.afks.add(un);
   client.updateList();
  } else {
   if (client.afks.contains(un)) {
    client.afks.remove(un);
    client.updateList();
   }
  }
  */
 }

 /**
  * signifies an error and reports it to the user
  * @param s the error message
  */
 public void error(String s) {
  HTMLDocument doc = (HTMLDocument) getDocument();
  HTMLEditorKit kit = (HTMLEditorKit) getEditorKit();
  try {
   kit.insertHTML(doc, doc.getLength(), "<font color=#CC0000><b>ERROR  : " + s + "<b><br></font>", 0,0,HTML.Tag.FONT);
   setCaretPosition(doc.getLength());
  } catch (Throwable t) { t.printStackTrace(); }
 }
 
 
 /**
  * adds status info
  * @param s the status message
  */
 public void addText(String s) {
  HTMLDocument doc = (HTMLDocument) getDocument();
  HTMLEditorKit kit = (HTMLEditorKit) getEditorKit();
  try {
   kit.insertHTML(doc, doc.getLength(), "<font color=#0000FF><b> " + s + "<b><br></font>", 0,0,HTML.Tag.FONT);
   setCaretPosition(doc.getLength());
  } catch (Throwable t) { t.printStackTrace(); }
 }
}
