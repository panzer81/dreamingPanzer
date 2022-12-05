package com.panframework.web.parameter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class PanParameterUtil {
  public static PanMap getParameterMap(HttpServletRequest request) {
    PanMap map = new PanMap();
    for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements(); ){
      String key = (String)e.nextElement();
      map.put(key, request.getParameter(key));
    }
    return map;
  }
  
  public static PanMapList getParameterListMap(HttpServletRequest request) {
    PanMapList listMap = new PanMapList();
    for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements(); ) {
      String key = (String)e.nextElement();
      String[] values = request.getParameterValues(key);
      List<String> al = new ArrayList<String>();
      for (String value : values) {
        al.add(value); 
      }
      listMap.put(key, al);
      
    } 
    return listMap;
  }
}