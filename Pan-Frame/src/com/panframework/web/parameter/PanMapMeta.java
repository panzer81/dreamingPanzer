package com.panframework.web.parameter;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class PanMapMeta extends LinkedHashMap<Object, Object> {
  private static final long serialVersionUID = 8647081260461931894L;
  
  public int getType(String key) {
    MetaData metaData = (MetaData)get(key);
    return metaData.getType();
  }
  
  public void setType(String paramString, int type) {
    Object obj = get(paramString);
    MetaData metaData = null;
    if (obj == null) {
      metaData = new MetaData(type, 0);
      put(paramString, metaData);
    } else {
      metaData = (MetaData)obj;
    } 
    metaData.setType(type);
  }
  
  public int getSize(String key) {
    MetaData metaData = (MetaData)get(key);
    return metaData.getSize();
  }
  
  public void setSize(String paramString, int size) {
    Object obj = get(paramString);
    MetaData metaData = null;
    if (obj == null) {
      metaData = new MetaData(0, size);
      put(paramString, metaData);
    } else {
      metaData = (MetaData)obj;
    } 
    metaData.setSize(size);
  }
  
  class MetaData implements Serializable {
    private static final long serialVersionUID = -5987461527368970855L;
    
    private int type;
    
    private int size;
    
    MetaData(int type, int size) {
      this.type = type;
      this.size = size;
    }
    
    MetaData(String paramString) {
      setMetaInfo(paramString);
    }
    
    public void setMetaInfo(String paramString) {
      String[] arrayOfString = paramString.split("\\|");
      if (arrayOfString == null || arrayOfString.length != 2) {
        System.out.println("Meta String: " + paramString);
      } else {
        try {
          setType(Integer.parseInt(arrayOfString[0]));
          setSize(Integer.parseInt(arrayOfString[1]));
        } catch (NumberFormatException nfe) {
          System.out.println("Meta String: " + paramString);
        } 
      } 
    }
    
    public String getMetaInfo() {
      return getType() + "|" + getSize();
    }
    
    public int getType() {
      return this.type;
    }
    
    public void setType(int type) {
      this.type = type;
    }
    
    public int getSize() {
      return this.size;
    }
    
    public void setSize(int size) {
      if (size < 0)
        System.out.println("Size: " + size); 
      this.size = size;
    }
  }
}