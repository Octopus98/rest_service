package com.example.queue;

import com.example.elastic.Demo;
import com.example.model.AdvancedPlan;
import com.example.model.LinkedPlanServiceHelper;
import com.example.model.Top;
import com.example.util.HttpRequestMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQReceiver {

  public RabbitMQReceiver() {
    try {
      Demo.indexMap(Config.INDEX_NAME);
      System.out.println("Succeed to create mapping.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    ConnectionFactory connectionFactory = new CachingConnectionFactory(Config.HOST, Config.PORT);
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(Config.QUEUE_NAME);
    container.setMessageListener(
        (ChannelAwareMessageListener)
            (message, channel) -> {
              String messageBody = new String(message.getBody());
              System.out.println("Received message: " + messageBody);

              HttpRequestMessage request = HttpRequestMessage.fromJson(messageBody);
              String jsonString = (String) request.getBody();
              String method = request.getMethod().toUpperCase();

              switch (method) {
                case "POST" -> {
                  ObjectMapper objectMapper = new ObjectMapper();
                  AdvancedPlan plan = objectMapper.readValue(jsonString, AdvancedPlan.class);
                  addPlan(plan);
                }
                case "PATCH" -> {
                  Demo.removeDocument(Config.INDEX_NAME);
                  ObjectMapper objectMapper = new ObjectMapper();
                  AdvancedPlan plan = objectMapper.readValue(jsonString, AdvancedPlan.class);
                  addPlan(plan);
                }
                case "DELETE" -> Demo.removeDocument(Config.INDEX_NAME);
              }
            });

    container.start();
  }

  private void addPlan(AdvancedPlan plan) {
    assert (plan != null);

    String objectId = plan.getObjectId();
    List<String> ids = new ArrayList<>();
    List<String> jsonStrings = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      String jsonString;
      Top top =
          new Top(
              plan.get_org(),
              plan.getObjectId(),
              plan.getObjectType(),
              plan.getPlanType(),
              plan.getCreationDate());
      top.getJoinField().put("name", "parent");

      // Derive PlanCostShares.
      AdvancedPlan.PlanCostShares planCostShares = plan.getPlanCostShares();
      planCostShares.getJoinField().put("name", "child");
      planCostShares.getJoinField().put("parent", objectId);

      jsonString = objectMapper.writeValueAsString(top);
      ids.add(objectId);
      jsonStrings.add(jsonString);

      jsonString = objectMapper.writeValueAsString(planCostShares);
      ids.add(planCostShares.getObjectId());
      jsonStrings.add(jsonString);

      // Derive linkedPlanServices.
      for (AdvancedPlan.LinkedPlanService linkedPlanService : plan.getLinkedPlanServices()) {
        LinkedPlanServiceHelper helper =
            new LinkedPlanServiceHelper(
                linkedPlanService.get_org(),
                linkedPlanService.getObjectId(),
                linkedPlanService.getObjectType());
        helper.getJoinField().put("name", "child");
        helper.getJoinField().put("parent", objectId);

        jsonString = objectMapper.writeValueAsString(helper);
        ids.add(helper.getObjectId());
        jsonStrings.add(jsonString);

        AdvancedPlan.LinkedService linkedService = linkedPlanService.getLinkedService();
        linkedService.getJoinField().put("name", "child");
        linkedService.getJoinField().put("parent", helper.getObjectId());

        jsonString = objectMapper.writeValueAsString(linkedService);
        ids.add(linkedService.getObjectId());
        jsonStrings.add(jsonString);

        AdvancedPlan.PlanServiceCostShares planServiceCostShares =
            linkedPlanService.getPlanserviceCostShares();
        planServiceCostShares.getJoinField().put("name", "child");
        planServiceCostShares.getJoinField().put("parent", helper.getObjectId());

        jsonString = objectMapper.writeValueAsString(planServiceCostShares);
        ids.add(planServiceCostShares.getObjectId());
        jsonStrings.add(jsonString);
      }

      for (int i = 0; i < ids.size(); ++i) {
        Demo.indexDocument(
            Config.INDEX_NAME, ids.get(i), jsonStrings.get(i), String.valueOf(i + 1));
        System.out.println("i = " + i);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new RabbitMQReceiver();
  }
}
