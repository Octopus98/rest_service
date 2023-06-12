package com.example.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class ElasticsearchHttpClient {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final String host;

  public ElasticsearchHttpClient(String host) {
    this.host = host;
  }

  public String get(String indexName, String docId) throws IOException {
    HttpGet httpGet = new HttpGet(host + "/" + indexName + "/_doc/" + docId);
    return executeHttpRequest(httpGet);
  }

  public String post(String indexName, String docId, String requestBody) throws IOException {
    // String requestBody = serializeDataToJson(data);
    HttpPost httpPost = new HttpPost(host + "/" + indexName + "/_doc/" + docId);
    httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

    return executeHttpRequest(httpPost);
  }

  public String patch(String indexName, String docId, Map<String, Object> data) throws IOException {
    String requestBody = serializeDataToJson(Map.of("doc", data));
    HttpPatch httpPatch = new HttpPatch(host + "/" + indexName + "/_doc/" + docId + "/_update");
    httpPatch.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

    return executeHttpRequest(httpPatch);
  }

  private String serializeDataToJson(Map<String, Object> data) throws JsonProcessingException {
    return objectMapper.writeValueAsString(data);
  }

  private String executeHttpRequest(HttpUriRequest httpRequest) throws IOException {
    try (CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequest)) {
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        String responseBody = EntityUtils.toString(entity);
        return responseBody;
      }
    }
    return null;
  }
}
