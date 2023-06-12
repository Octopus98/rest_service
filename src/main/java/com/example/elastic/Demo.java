package com.example.elastic;

import com.example.queue.Config;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

public class Demo {
  static RestHighLevelClient client =
      new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

  // Make sure index name is unique
  public static void indexMap(String indexName) throws IOException {
    CreateIndexRequest request = new CreateIndexRequest(indexName);
    request.mapping(
        "{\n"
            + "    \"properties\": {\n"
            + "        \"message\": {\n"
            + "            \"type\": \"text\"\n"
            + "        },\n"
            + "        \"join_field\": {\n"
            + "            \"type\": \"join\",\n"
            + "            \"relations\": {\n"
            + "                \"parent\": \"child\"\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}",
        XContentType.JSON);

    CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
  }

  public static void indexDocument(
      String indexName, String id, String jsonString, String routeValue) throws IOException {

    IndexRequest request = new IndexRequest(indexName);
    request.id(id);
    request.routing(routeValue);
    request.source(jsonString, XContentType.JSON);

    IndexResponse response = client.index(request, RequestOptions.DEFAULT);
  }

  public static void removeDocument(String indexName) throws IOException {
    DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
    request.setQuery(QueryBuilders.matchAllQuery());
    BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

    long deleted = response.getDeleted();
    System.out.println("Deleted " + deleted + " documents");
  }

  public static void main(String[] args) throws IOException {
    Demo.removeDocument(Config.INDEX_NAME);
  }
}
