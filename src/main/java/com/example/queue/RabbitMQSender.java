package com.example.queue;

import com.example.util.HttpRequestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQSender {
  private final ConnectionFactory factory;
  private Connection connection;
  private Channel channel;

  public RabbitMQSender() {
    factory = new ConnectionFactory();
    factory.setHost(Config.HOST);
    factory.setPort(Config.PORT);
    factory.setUsername(Config.USERNAME);
    factory.setPassword(Config.PASSWORD);
  }

  public void send(HttpRequestMessage request) throws IOException, TimeoutException {
    String jsonString = request.toJson();

    try {
      connection = factory.newConnection();
      channel = connection.createChannel();
      channel.queueDeclare(Config.QUEUE_NAME, false, false, false, null);
      channel.basicPublish(
          "", Config.QUEUE_NAME, null, jsonString.getBytes(StandardCharsets.UTF_8));
      System.out.println(" [x] Sent '" + jsonString + "'");
    } finally {
      if (channel != null) {
        channel.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    RabbitMQSender sender = new RabbitMQSender();

    String json = """
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


    try {
      Map<String, String> header = new HashMap<>();
      HttpRequestMessage requestMessage = new HttpRequestMessage(
              "POST", "plan", header, json
      );
      sender.send(requestMessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
