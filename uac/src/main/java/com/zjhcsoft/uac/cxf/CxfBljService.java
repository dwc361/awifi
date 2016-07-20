package com.zjhcsoft.uac.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface CxfBljService {
	/**
	 * 
	 * @param clientXML
	 * @return
	 */
	public String CheckAiuapTokenSoap(@WebParam(name = "RequestInfo") String RequestInfo);
	
	//public String CheckAiuapTokenSoap();
	
	//@WebResult
	//public String CheckAiuapTokenSoap(@WebParam(name = "RequestInfo") String RequestInfo);
	
	
	//public ResponseInfo CheckAiuapTokenSoap(@WebParam(name = "RequestInfo") RequestInfo RequestInfo);


	
}