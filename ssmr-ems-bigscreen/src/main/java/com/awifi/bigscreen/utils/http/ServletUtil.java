package com.awifi.bigscreen.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.MimeHeaders;

public class ServletUtil {

    /**
     * 获取HTTP请求的请求IP
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取HTTP请求包头信息
     * 
     * @param req
     * @return
     */
    @SuppressWarnings("unchecked")
    public static MimeHeaders getHeaders(HttpServletRequest req) {
        Enumeration<String> enumt = req.getHeaderNames();
        MimeHeaders headers = new MimeHeaders();
        while (enumt.hasMoreElements()) {
            String headerName = enumt.nextElement();
            String headerValue = req.getHeader(headerName);
            StringTokenizer values = new StringTokenizer(headerValue, ",");
            while (values.hasMoreTokens()) {
                headers.addHeader(headerName, values.nextToken().trim());
            }
        }
        return headers;
    }

    /**
     * 从http请求从读取body中的信息
     * 
     * @param req
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getReqBodyString(HttpServletRequest req, String encoding) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), encoding));
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        }
        return sb.toString();
    }

    /**
     * 读取请求HTTP协议的Body内容
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static String readReqBody(HttpServletRequest request, String contenttype, String charset) throws Exception {
        String reqmsg = "";
        if(charset.equals(contenttype)) {
        	charset = "UTF-8";
        }

        if (null != contenttype
                && contenttype.length() > 0
                && contenttype.contains("application/x-www-form-urlencoded")) {
            String key = "";
            String value = "";
            StringBuffer sb = new StringBuffer();

            Iterator<String> it = request.getParameterMap().keySet().iterator();
            while (it.hasNext()) {
                key = it.next();
                value = (request.getParameterMap().get(key))[0].toString();

                sb.append(key + "=" + value);
            }
            reqmsg = sb.toString();
        } else {
            reqmsg = ServletUtil.getReqBodyString(request, charset);
        }
        return reqmsg;
    }

    /**
     * 读取请求HTTP协议的Body内容--默认按照UTF-8的模式进行数据的读取
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static String readReqBodyUTF8(HttpServletRequest request, String contenttype, String charset)
            throws Exception {
        String reqmsg = "";

        if (null != contenttype
                && contenttype.length() > 0
                && contenttype.contains("application/x-www-form-urlencoded")) {
            //			String key = "";
            //			String value = "";
            //			StringBuffer sb = new StringBuffer();
            //
            //			Iterator<String> it = request.getParameterMap().keySet().iterator();
            //			while (it.hasNext()) {
            //				key = it.next();
            //				value = ((Object[]) (request.getParameterMap().get(key)))[0].toString();
            //
            //				sb.append(key + "=" + value);
            //			}
            //			reqmsg = sb.toString();
            reqmsg = ServletUtil.getReqBodyString(request, "UTF-8");
        } else {
            reqmsg = ServletUtil.getReqBodyString(request, charset);
        }
        return reqmsg;
    }

    /**
     * 获取请求中的系统编码，先从HTTP协议报文头信息中用关键字SystemCode查找，如果没有，则在querystring中查找，都没有，返回null
     * 
     * @param requestURL
     * @param headers
     * @return
     */
    public static String getSystemCode(String requestURL, MimeHeaders headers) {
        //先从HTTP协议报文头信息中用关键字SystemCode查找
        String[] SystemCodes = headers.getHeader("SystemCode");
        if (null != SystemCodes && SystemCodes.length > 0) {
            return SystemCodes[0];
        }

        //在querystring中查找
        if (requestURL != null && requestURL.indexOf("SystemCode") >= 0) {
            String substr = requestURL.substring(requestURL.indexOf("SystemCode"));
            if (substr.indexOf("&") >= 0) {
                return substr.substring(11, substr.indexOf("&"));
            } else {
                return substr.substring(11);
            }
        }

        return null;
    }
}
