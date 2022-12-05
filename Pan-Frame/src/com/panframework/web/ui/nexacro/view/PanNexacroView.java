package com.panframework.web.ui.nexacro.view;

import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.DataSetList;
import com.nexacro.java.xapi.data.PlatformData;
import com.nexacro.java.xapi.data.VariableList;
import com.nexacro.java.xapi.tx.HttpPlatformResponse;
import com.panframework.web.parameter.PanMap;
import com.panframework.web.ui.nexacro.PanNexacroData;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class PanNexacroView extends AbstractView {
  private static final Logger log = LoggerFactory.getLogger(PanNexacroView.class);
  
  private String contentType = null;
  
  public PanNexacroView() {
    this.contentType = "PlatformXml";
  }
  
  public PanNexacroView(String contentType) {
    this.contentType = contentType;
  }
  
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    PlatformData platformData = new PlatformData();
    PanNexacroData nexaData = (PanNexacroData)model.get("PanNexacroData");
    if (nexaData == null) {
      log.error("No data to response!\nPlease check the key name of ModelAndView.addObject().");
      return;
    } 
    VariableList varList = convertToVariableList(nexaData);
    platformData.setVariableList(varList);
    DataSetList dsList = convertToDataSetList(nexaData);
    platformData.setDataSetList(dsList);
    HttpPlatformResponse hpResponse = new HttpPlatformResponse(response);
    hpResponse.setContentType(this.contentType);
    hpResponse.setCharset("UTF-8");
    hpResponse.setData(platformData);
    hpResponse.sendData();
  }
  
  private VariableList convertToVariableList(PanNexacroData nexaData) {
    VariableList varList = new VariableList();
    varList.add("ErrorCode", nexaData.getErrorCode());
    varList.add("ErrorMsg", nexaData.getErrorMsg());
    if (StringUtils.hasLength(nexaData.getRequiredKey()))
      varList.add("RequiredKey", nexaData.getRequiredKey()); 
    PanMap varMap = nexaData.getResponseVariable();
    if (varMap != null && !varMap.isEmpty()) {
      Iterator<?> itr = varMap.keySet().iterator();
      while (itr.hasNext()) {
        String key = (String)itr.next();
        varList.add(key, varMap.get(key));
      } 
    } 
    return varList;
  }
  
  private DataSetList convertToDataSetList(PanNexacroData nexaData) {
    DataSetList dsList = new DataSetList();
    PanMap resDataMap = nexaData.getResponseData();
    if (resDataMap == null || resDataMap.isEmpty())
      return dsList; 
    Iterator<?> itr = resDataMap.keySet().iterator();
    while (itr.hasNext()) {
      String key = (String)itr.next();
      DataSet ds = convertObjectToDataSet(key, resDataMap.get(key));
      dsList.add(ds);
    } 
    return dsList;
  }
  
  private DataSet convertObjectToDataSet(String key, Object value) {
    DataSet ds = new DataSet(key);
    if (value instanceof java.util.Collection) {
      List<PanMap> dataList = (List<PanMap>)value;
      if (dataList == null || dataList.isEmpty())
        return ds; 
      makeDataSetColumn(dataList.get(0), ds);
      convertListToDataSet(dataList, ds);
    } else if (value instanceof Map) {
      PanMap dataMap = (PanMap)value;
      if (dataMap == null || dataMap.isEmpty())
        return ds; 
      makeDataSetColumn(dataMap, ds);
      convertPanMapToDataSet(dataMap, ds);
    } 
    return ds;
  }
  
  private void makeDataSetColumn(PanMap dataMap, DataSet ds) {
    ds.setChangeStructureWithData(true);
    Iterator<?> itr = dataMap.keySet().iterator();
    while (itr.hasNext()) {
      String colNm = (String)itr.next();
      ds.addColumn(colNm, 2);
    } 
    ds.setChangeStructureWithData(false);
  }
  
  private DataSet convertListToDataSet(List<PanMap> dataList, DataSet ds) {
    if (dataList == null || dataList.isEmpty())
      return ds; 
    for (PanMap dataMap : dataList) {
      int rowIdx = ds.newRow();
      Iterator<?> itr = dataMap.keySet().iterator();
      while (itr.hasNext()) {
        String colNm = (String)itr.next();
        ds.set(rowIdx, colNm, dataMap.getString(colNm));
      } 
    } 
    return ds;
  }
  
  private DataSet convertPanMapToDataSet(PanMap dataMap, DataSet ds) {
    if (dataMap == null || dataMap.isEmpty())
      return ds; 
    int rowIdx = ds.newRow();
    Iterator<?> itr = dataMap.keySet().iterator();
    while (itr.hasNext()) {
      String colNm = (String)itr.next();
      ds.set(rowIdx, colNm, dataMap.getString(colNm));
    } 
    return ds;
  }
  
  public void sendNexacroResponse(HttpServletRequest request, HttpServletResponse response, PanNexacroData nexaData) throws Exception {
    PlatformData platformData = new PlatformData();
    VariableList varList = convertToVariableList(nexaData);
    platformData.setVariableList(varList);
    DataSetList dsList = convertToDataSetList(nexaData);
    platformData.setDataSetList(dsList);
    HttpPlatformResponse hpResponse = new HttpPlatformResponse(response);
    hpResponse.setContentType(this.contentType);
    hpResponse.setCharset("UTF-8");
    hpResponse.setData(platformData);
    hpResponse.sendData();
  }
}