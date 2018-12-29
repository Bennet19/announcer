package net.madgamble.bennet.bungeeannouncer.utils;

import java.util.HashMap;
import java.util.Map;

public enum FontFormat
{
  BLACK("0"),  DARK_BLUE("1"),  DARK_GREEN("2"),  DARK_AQUA("3"),  DARK_RED("4"),  PURPLE("5"),  ORANGE("6"),  GREY("7"),  DARK_GREY("8"),  BLUE("9"),  GREEN("a"),  AQUA("b"),  RED("c"),  PINK("d"),  YELLOW("e"),  WHITE("f"),  RANDOM("k"),  BOLD("l"),  STRIKE("m"),  UNDERLINED("n"),  ITALICS("o"),  RESET("r");
  
  private final String value;
  private static final String characterValue = "�";
  private static final Map<String, String> translate;
  
  private FontFormat(String value)
  {
    this.value = ("�" + value);
  }
  
  static
  {
    translate = new HashMap();
    createMap();
  }
  
  public String toString()
  {
    return this.value;
  }
  
  private static void createMap()
  {
    translate.put("&0", "�0");
    translate.put("&1", "�1");
    translate.put("&2", "�2");
    translate.put("&3", "�3");
    translate.put("&4", "�4");
    translate.put("&5", "�5");
    translate.put("&6", "�6");
    translate.put("&7", "�7");
    translate.put("&8", "�8");
    translate.put("&9", "�9");
    translate.put("&a", "�a");
    translate.put("&b", "�b");
    translate.put("&c", "�c");
    translate.put("&d", "�d");
    translate.put("&e", "�e");
    translate.put("&f", "�f");
    translate.put("&k", "�k");
    translate.put("&l", "�l");
    translate.put("&m", "�m");
    translate.put("&n", "�n");
    translate.put("&o", "�o");
    translate.put("&r", "�r");
  }
  
  public static String translateString(String value)
  {
    for (String code : translate.keySet()) {
      value = value.replace(code, (CharSequence)translate.get(code));
    }
    return value;
  }
}
