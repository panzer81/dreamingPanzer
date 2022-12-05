package com.panframework.web.ui.nexacro.resolver;

import com.nexacro.java.xapi.data.PlatformData;
import com.nexacro.java.xapi.tx.HttpPlatformRequest;
import com.panframework.web.ui.nexacro.PanNexacroData;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PanNexacroArgumentResolver implements HandlerMethodArgumentResolver {
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    PanNexacroData nexaData = new PanNexacroData();
    HttpPlatformRequest nexaRequest = null;
    try {
      HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
      nexaData.setRequestParameter(request);
      nexaRequest = new HttpPlatformRequest(request);
      nexaRequest.receiveData();
    } catch (Exception e) {
      e.getStackTrace();
      throw new Exception("HttpPlatformRequest error");
    } 
    PlatformData platformData = nexaRequest.getData();
    nexaData.setVariableParameterMap(platformData.getVariableList());
    nexaData.setDataSetParameterMap(platformData.getDataSetList());
    return nexaData;
  }
  
  public boolean supportsParameter(MethodParameter parameter) {
    return PanNexacroData.class.isAssignableFrom(parameter.getParameterType());
  }
}