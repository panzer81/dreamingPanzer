package com.panframework.web.ui.nexacro.view;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.panframework.web.parameter.PanMap;
import com.panframework.web.ui.nexacro.PanNexacroData;

public class PanNexacroSsvView extends AbstractView {
  private static final Logger log = LoggerFactory.getLogger(PanNexacroSsvView.class);
  
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    PanNexacroData nexaData = (PanNexacroData)model.get("PanNexacroData");
    if (nexaData == null) {
      log.error("No data to response!\nPlease check the key name of ModelAndView.addObject().");
      return;
    } 
    Writer writer = response.getWriter();
    writer.write("CSV:UTF-8\n");
    writer.write(convertToVariableString(nexaData));
    writer.write(convertToDataSetString(nexaData));
    writer.flush();
    writer.close();
  }
  
  private String convertToVariableString(PanNexacroData nexaData) {
    StringBuffer sbVar = new StringBuffer();
    sbVar.append("ErrorCode").append("=").append(nexaData.getErrorCode()).append("\n");
    sbVar.append("ErrorMsg").append("=").append(nexaData.getErrorMsg()).append("\n");
    PanMap varMap = nexaData.getResponseVariable();
    if (varMap != null && !varMap.isEmpty()) {
      Iterator<?> itr = varMap.keySet().iterator();
      while (itr.hasNext()) {
        String key = (String)itr.next();
        sbVar.append(key).append("=").append(varMap.get(key)).append("\n");
      } 
    } 
    return sbVar.toString();
  }
  
  private String convertToDataSetString(PanNexacroData nexaData) {
    StringBuffer sbDs = new StringBuffer();
    PanMap resDataMap = nexaData.getResponseData();
    if (resDataMap == null || resDataMap.isEmpty())
      return sbDs.toString(); 
    Iterator<?> itr = resDataMap.keySet().iterator();
    while (itr.hasNext()) {
      String key = (String)itr.next();
      sbDs.append(convertObjectToDataSetString(key, resDataMap.get(key)));
    } 
    return sbDs.toString();
  }
  
  private StringBuffer convertObjectToDataSetString(String key, Object value) {
    StringBuffer sbDs = new StringBuffer();
    sbDs.append("Dataset:").append(key).append("\n");
    if (value instanceof String) {
      if (StringUtils.isEmpty(value))
        return sbDs; 
      sbDs.append(value);
    } else if (value instanceof byte[]) {
      byte[] byteValue = (byte[])value;
      if (byteValue.length == 0)
        return sbDs; 
      sbDs.append(decompress((byte[])value));
    } 
    return sbDs;
  }
  
  private String decompress(byte[] value) {
    String result = null;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPInputStream gzipis = new GZIPInputStream(new BufferedInputStream(new ByteArrayInputStream(value)));
      int size = 0;
      byte[] b = new byte[1024];
      while ((size = gzipis.read(b)) > 0)
        baos.write(b, 0, size); 
      baos.flush();
      baos.close();
      result = new String(baos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return result;
  }
}