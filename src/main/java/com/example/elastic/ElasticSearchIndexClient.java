package com.example.elastic;

import com.example.model.AdvancedPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

public class ElasticSearchIndexClient {

  private RestHighLevelClient client;

  public ElasticSearchIndexClient() {
    this.client =
        new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
  }

  public void indexDocument(String index, String jsonString) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      AdvancedPlan plan = objectMapper.readValue(jsonString, AdvancedPlan.class);


    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public void close() throws IOException {
    client.close();
  }

  public void createMap() throws IOException {
    CreateIndexRequest request = new CreateIndexRequest("my_index");
    String mapping = "{\n" +
            "    \"properties\": {\n" +
            "      \"my_join_field\": {\n" +
            "        \"type\": \"join\",\n" +
            "        \"relations\": {\n" +
            "          \"parent\": \"child\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "}";
    request.mapping(mapping, XContentType.JSON);
    CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
    System.out.println(createIndexResponse.toString());
  }

  public void test() {
    ObjectMapper objectMapper = new ObjectMapper();
    ParentObject parentObject = new ParentObject("Guo Cheng", "parent-id-123");
    ChildObject childObject = new ChildObject("small", "Child 1");

    try {
      createMap();

      IndexRequest parentRequest = new IndexRequest("my_index")
              .id("1")
              .source(XContentType.JSON,
                      "my_join_field", "parent",
                      "name", "parent-doc");

      IndexRequest childRequest = new IndexRequest("my_index")
              .id("2")
              .source(XContentType.JSON,
                      "my_join_field", "child",
                      "name", "child-doc",
                      "parent", "1",
                      "field1", "value1",
                      "field2", "value2");

      IndexResponse parentResponse = client.index(parentRequest, RequestOptions.DEFAULT);
      IndexResponse childResponse = client.index(childRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws IOException {

    ElasticSearchIndexClient client = new ElasticSearchIndexClient();
    client.test();

    // client.test();

    String jsonString = """
{

	"planCostShares": {
		"deductible": 2000,
		"_org": "example.com",
		"copay": 23,
		"objectId": "1234vxc2324sdf-501",
		"objectType": "membercostshare"
		
	},
	"linkedPlanServices": [{
		"linkedService": {
			"_org": "example.com",
			"objectId": "1234520xvc30asdf-502",
			"objectType": "service",
			"name": "Yearly physical"
		},
		"planserviceCostShares": {
			"deductible": 10,
			"_org": "example.com",
			"copay": 0,
			"objectId": "1234512xvc1314asdfs-503",
			"objectType": "membercostshare"
		},
		"_org": "example.com",
		"objectId": "27283xvx9asdff-504",
		"objectType": "planservice"
	}, {
		"linkedService": {
			"_org": "example.com",
			"objectId": "1234520xvc30sfs-505",
			"objectType": "service",
			"name": "well baby"
		},
		"planserviceCostShares": {
			"deductible": 10,
			"_org": "example.com",
			"copay": 175,
			"objectId": "1234512xvc1314sdfsd-506",
			"objectType": "membercostshare"
		},
		
		"_org": "example.com",
		
		"objectId": "27283xvx9sdf-507",
		"objectType": "planservice"
	}],


	"_org": "example.com",
	"objectId": "12xvxc345ssdsds-508",
	"objectType": "plan",
	"planType": "inNetwork",
	"creationDate": "12-12-2017"
}            
""";



  }
}
