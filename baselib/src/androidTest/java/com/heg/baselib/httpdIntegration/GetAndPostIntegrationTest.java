package com.heg.baselib.httpdIntegration;


import com.heg.baselib.httpserver.core.NanoHTTPD;
import com.heg.baselib.httpserver.core.NanoHTTPD.Response.Status;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class GetAndPostIntegrationTest extends IntegrationTestBase<GetAndPostIntegrationTest.HTTPDTestServer> {

    public static class HTTPDTestServer extends NanoHTTPD {

        public String response;

        public HTTPDTestServer() {
            super(8192);
        }

        @Override
        public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
            StringBuilder sb = new StringBuilder(String.valueOf(method) + ':' + this.response);

            if (parms.size() > 1) {
                parms.remove("NanoHttpd.QUERY_STRING");
                sb.append("-params=").append(parms.size());
                List<String> p = new ArrayList<String>(parms.keySet());
                Collections.sort(p);
                for (String k : p) {
                    sb.append(';').append(k).append('=').append(parms.get(k));
                }
            }
            if ("/encodingtest".equals(uri)) {
                return newFixedLengthResponse(Status.OK, MIME_HTML, "<html><head><title>Testé ça</title></head><body>Testé ça</body></html>");
            } else if ("/chin".equals(uri)) {
                return newFixedLengthResponse(Status.OK, "application/octet-stream", sb.toString());
            } else {
                return newFixedLengthResponse(sb.toString());
            }
        }
    }

    @Override
    public HTTPDTestServer createTestServer() {
        return new HTTPDTestServer();
    }

    /**
     * 不带参 Get请求
     * @throws Exception
     */
    public void testSimpleGetRequest() throws Exception {
        this.testServer.response = "testSimpleGetRequest";

        HttpGet httpget = new HttpGet("http://localhost:8192/");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = this.httpclient.execute(httpget, responseHandler);

        assertEquals("GET:testSimpleGetRequest", responseBody);
    }

    /**
     * 带参 Get请求
     * @throws Exception
     */
    public void testGetRequestWithParameters() throws Exception {
        this.testServer.response = "testGetRequestWithParameters";

        HttpGet httpget = new HttpGet("http://localhost:8192/?age=120&gender=Male");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = this.httpclient.execute(httpget, responseHandler);

        assertEquals("GET:testGetRequestWithParameters-params=2;age=120;gender=Male", responseBody);
    }

    /**
     * 不带参 Post请求
     * @throws Exception
     */
    public void testPostWithNoParameters() throws Exception {
        this.testServer.response = "testPostWithNoParameters";

        HttpPost httppost = new HttpPost("http://localhost:8192/");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = this.httpclient.execute(httppost, responseHandler);

        assertEquals("POST:testPostWithNoParameters", responseBody);
    }

    /**
     * 带格式化编码参数 Post请求
     * @throws Exception
     */
    public void testPostRequestWithFormEncodedParameters() throws Exception {
        this.testServer.response = "testPostRequestWithFormEncodedParameters";

        HttpPost httppost = new HttpPost("http://localhost:8192/");
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("age", "120"));
        postParameters.add(new BasicNameValuePair("gender", "Male"));
        httppost.setEntity(new UrlEncodedFormEntity(postParameters));

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = this.httpclient.execute(httppost, responseHandler);

        assertEquals("POST:testPostRequestWithFormEncodedParameters-params=2;age=120;gender=Male", responseBody);
    }


    /**
     * Post 参数编码请求
     * @throws Exception
     */
    public void testPostRequestWithEncodedParameters() throws Exception {
        this.testServer.response = "testPostRequestWithEncodedParameters";

        HttpPost httppost = new HttpPost("http://localhost:8192/encodingtest");
        HttpResponse response = this.httpclient.execute(httppost);

        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        assertEquals("<html><head><title>Testé ça</title></head><body>Testé ça</body></html>", responseBody);
    }

}
