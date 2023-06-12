package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedPlan {
  private PlanCostShares planCostShares;
  private List<LinkedPlanService> linkedPlanServices;
  private String _org;
  private String objectId;
  private String objectType;
  private String planType;
  private String creationDate;

  public AdvancedPlan() {

  }

    public AdvancedPlan(PlanCostShares planCostShares, List<LinkedPlanService> linkedPlanServices, String _org, String objectId, String objectType, String planType, String creationDate) {
        this.planCostShares = planCostShares;
        this.linkedPlanServices = linkedPlanServices;
        this._org = _org;
        this.objectId = objectId;
        this.objectType = objectType;
        this.planType = planType;
        this.creationDate = creationDate;
    }

    public PlanCostShares getPlanCostShares() {
        return planCostShares;
    }

    public void setPlanCostShares(PlanCostShares planCostShares) {
        this.planCostShares = planCostShares;
    }

    public List<LinkedPlanService> getLinkedPlanServices() {
        return linkedPlanServices;
    }

    public void setLinkedPlanServices(List<LinkedPlanService> linkedPlanServices) {
        this.linkedPlanServices = linkedPlanServices;
    }

    public String get_org() {
        return _org;
    }

    public void set_org(String _org) {
        this._org = _org;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    // Getters and setters omitted for brevity

  public static class PlanCostShares {
    private int deductible;
    private String _org;
    private int copay;
    private String objectId;
    private String objectType;
    public Map<String, String> getJoinField() {
      return joinField;
    }

    public void setJoinField(Map<String, String> joinField) {
      this.joinField = joinField;
    }

    @JsonProperty(value="join_field")
    private Map<String, String> joinField;

      public PlanCostShares() {
        joinField = new HashMap<>();
      }
    public PlanCostShares(
        int deductible, String _org, int copay, String objectId, String objectType) {
      this.deductible = deductible;
      this._org = _org;
      this.copay = copay;
      this.objectId = objectId;
      this.objectType = objectType;
    }

    public int getDeductible() {
      return deductible;
    }

    public void setDeductible(int deductible) {
      this.deductible = deductible;
    }

    public String get_org() {
      return _org;
    }

    public void set_org(String _org) {
      this._org = _org;
    }

    public int getCopay() {
      return copay;
    }

    public void setCopay(int copay) {
      this.copay = copay;
    }

    public String getObjectId() {
      return objectId;
    }

    public void setObjectId(String objectId) {
      this.objectId = objectId;
    }

    public String getObjectType() {
      return objectType;
    }

    public void setObjectType(String objectType) {
      this.objectType = objectType;
    }
  }

  public static class LinkedPlanService {
    private LinkedService linkedService;
    private PlanServiceCostShares planserviceCostShares;
    private String _org;
    private String objectId;
    private String objectType;

    public LinkedPlanService() {

    }

    public LinkedPlanService(
        LinkedService linkedService,
        PlanServiceCostShares planserviceCostShares,
        String _org,
        String objectId,
        String objectType) {
      this.linkedService = linkedService;
      this.planserviceCostShares = planserviceCostShares;
      this._org = _org;
      this.objectId = objectId;
      this.objectType = objectType;
    }

    public LinkedService getLinkedService() {
      return linkedService;
    }

    public void setLinkedService(LinkedService linkedService) {
      this.linkedService = linkedService;
    }

    public PlanServiceCostShares getPlanserviceCostShares() {
      return planserviceCostShares;
    }

    public void setPlanserviceCostShares(PlanServiceCostShares planserviceCostShares) {
      this.planserviceCostShares = planserviceCostShares;
    }

    public String get_org() {
      return _org;
    }

    public void set_org(String _org) {
      this._org = _org;
    }

    public String getObjectId() {
      return objectId;
    }

    public void setObjectId(String objectId) {
      this.objectId = objectId;
    }

    public String getObjectType() {
      return objectType;
    }

    public void setObjectType(String objectType) {
      this.objectType = objectType;
    }

    // Getters and setters omitted for brevity
  }

  public static class LinkedService {
    private String _org;
    private String objectId;
    private String objectType;
    private String name;
    public Map<String, String> getJoinField() {
      return joinField;
    }

    public void setJoinField(Map<String, String> joinField) {
      this.joinField = joinField;
    }

    @JsonProperty(value="join_field")
    private Map<String, String> joinField;

      public LinkedService() {
        joinField = new HashMap<>();
      }

    public LinkedService(String _org, String objectId, String objectType, String name) {
      this._org = _org;
      this.objectId = objectId;
      this.objectType = objectType;
      this.name = name;
    }

    public String get_org() {
      return _org;
    }

    public void set_org(String _org) {
      this._org = _org;
    }

    public String getObjectId() {
      return objectId;
    }

    public void setObjectId(String objectId) {
      this.objectId = objectId;
    }

    public String getObjectType() {
      return objectType;
    }

    public void setObjectType(String objectType) {
      this.objectType = objectType;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    // Getters and setters omitted for brevity
  }

  public static class PlanServiceCostShares {
    private int deductible;
    private String _org;
    private int copay;
    private String objectId;
    private String objectType;
    public Map<String, String> getJoinField() {
      return joinField;
    }

    public void setJoinField(Map<String, String> joinField) {
      this.joinField = joinField;
    }

    @JsonProperty(value="join_field")
    private Map<String, String> joinField;

      public PlanServiceCostShares() {
        joinField = new HashMap<>();

      }

    public PlanServiceCostShares(
        int deductible, String _org, int copay, String objectId, String objectType) {
      this.deductible = deductible;
      this._org = _org;
      this.copay = copay;
      this.objectId = objectId;
      this.objectType = objectType;
    }

    public int getDeductible() {
      return deductible;
    }

    public void setDeductible(int deductible) {
      this.deductible = deductible;
    }

    public String get_org() {
      return _org;
    }

    public void set_org(String _org) {
      this._org = _org;
    }

    public int getCopay() {
      return copay;
    }

    public void setCopay(int copay) {
      this.copay = copay;
    }

    public String getObjectId() {
      return objectId;
    }

    public void setObjectId(String objectId) {
      this.objectId = objectId;
    }

    public String getObjectType() {
      return objectType;
    }

    public void setObjectType(String objectType) {
      this.objectType = objectType;
    }
  }

  public static void main(String[] args) {
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

    ObjectMapper objectMapper = new ObjectMapper();

      try {
          AdvancedPlan plan = objectMapper.readValue(json, AdvancedPlan.class);
          String result = objectMapper.writeValueAsString(plan);
          System.out.println(result);
      } catch (JsonProcessingException e) {
          e.printStackTrace();
      }
  }
}
