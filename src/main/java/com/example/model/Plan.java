package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "planCostShares",
  "linkedPlanServices",
  "_org",
  "objectId",
  "objectType",
  "planType",
  "creationDate"
})
@Generated("jsonschema2pojo")
public class Plan {

  /** (Required) */
  @JsonProperty("planCostShares")
  private PlanCostShares planCostShares;
  /** (Required) */
  @JsonProperty("linkedPlanServices")
  private List<Object> linkedPlanServices;
  /** (Required) */
  @JsonProperty("_org")
  private String _org;
  /** (Required) */
  @JsonProperty("objectId")
  private String objectId;
  /** (Required) */
  @JsonProperty("objectType")
  private String objectType;
  /** (Required) */
  @JsonProperty("planType")
  private String planType;
  /** (Required) */
  @JsonProperty("creationDate")
  private String creationDate;

  /** No args constructor for use in serialization */
  public Plan() {}

  /**
   * Construct a plan.
   *
   * @param planCostShares plan cost shares
   * @param linkedPlanServices linked plan services
   * @param planType plan type
   * @param org organization
   * @param creationDate creation date
   * @param objectId object id
   * @param objectType object type
   */
  public Plan(
      PlanCostShares planCostShares,
      List<Object> linkedPlanServices,
      String org,
      String objectId,
      String objectType,
      String planType,
      String creationDate) {
    super();
    this.planCostShares = planCostShares;
    this.linkedPlanServices = linkedPlanServices;
    this._org = org;
    this.objectId = objectId;
    this.objectType = objectType;
    this.planType = planType;
    this.creationDate = creationDate;
  }

  /** (Required) */
  @JsonProperty("planCostShares")
  public PlanCostShares getPlanCostShares() {
    return planCostShares;
  }

  /** (Required) */
  @JsonProperty("planCostShares")
  public void setPlanCostShares(PlanCostShares planCostShares) {
    this.planCostShares = planCostShares;
  }

  public Plan withPlanCostShares(PlanCostShares planCostShares) {
    this.planCostShares = planCostShares;

    return this;
  }

  /** (Required) */
  @JsonProperty("linkedPlanServices")
  public List<Object> getLinkedPlanServices() {
    return linkedPlanServices;
  }

  /** (Required) */
  @JsonProperty("linkedPlanServices")
  public void setLinkedPlanServices(List<Object> linkedPlanServices) {
    this.linkedPlanServices = linkedPlanServices;
  }

  public Plan withLinkedPlanServices(List<Object> linkedPlanServices) {
    this.linkedPlanServices = linkedPlanServices;

    return this;
  }

  /** (Required) */
  @JsonProperty("_org")
  public String getOrg() {
    return _org;
  }

  /** (Required) */
  @JsonProperty("_org")
  public void setOrg(String org) {
    this._org = org;
  }

  public Plan withOrg(String org) {
    this._org = org;

    return this;
  }

  /** (Required) */
  @JsonProperty("objectId")
  public String getObjectId() {
    return objectId;
  }

  /** (Required) */
  @JsonProperty("objectId")
  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public Plan withObjectId(String objectId) {
    this.objectId = objectId;

    return this;
  }

  /** (Required) */
  @JsonProperty("objectType")
  public String getObjectType() {
    return objectType;
  }

  /** (Required) */
  @JsonProperty("objectType")
  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  public Plan withObjectType(String objectType) {
    this.objectType = objectType;

    return this;
  }

  /** (Required) */
  @JsonProperty("planType")
  public String getPlanType() {
    return planType;
  }

  /** (Required) */
  @JsonProperty("planType")
  public void setPlanType(String planType) {
    this.planType = planType;
  }

  public Plan withPlanType(String planType) {
    this.planType = planType;

    return this;
  }

  /** (Required) */
  @JsonProperty("creationDate")
  public String getCreationDate() {
    return creationDate;
  }

  /** (Required) */
  @JsonProperty("creationDate")
  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public Plan withCreationDate(String creationDate) {
    this.creationDate = creationDate;

    return this;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = ((result * 31) + ((this.planCostShares == null) ? 0 : this.planCostShares.hashCode()));
    result =
        ((result * 31)
            + ((this.linkedPlanServices == null) ? 0 : this.linkedPlanServices.hashCode()));
    result = ((result * 31) + ((this.planType == null) ? 0 : this.planType.hashCode()));
    result = ((result * 31) + ((this._org == null) ? 0 : this._org.hashCode()));
    result = ((result * 31) + ((this.creationDate == null) ? 0 : this.creationDate.hashCode()));
    result = ((result * 31) + ((this.objectId == null) ? 0 : this.objectId.hashCode()));
    result = ((result * 31) + ((this.objectType == null) ? 0 : this.objectType.hashCode()));

    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof Plan) == false) {
      return false;
    }

    Plan rhs = ((Plan) other);
    return ((((((((this.planCostShares == rhs.planCostShares)
                                || ((this.planCostShares != null)
                                    && this.planCostShares.equals(rhs.planCostShares)))
                            && ((this.linkedPlanServices == rhs.linkedPlanServices)
                                || ((this.linkedPlanServices != null)
                                    && this.linkedPlanServices.equals(rhs.linkedPlanServices))))
                        && ((this.planType == rhs.planType)
                            || ((this.planType != null) && this.planType.equals(rhs.planType))))
                    && ((this._org == rhs._org)
                        || ((this._org != null) && this._org.equals(rhs._org))))
                && ((this.creationDate == rhs.creationDate)
                    || ((this.creationDate != null) && this.creationDate.equals(rhs.creationDate))))
            && ((this.objectId == rhs.objectId)
                || ((this.objectId != null) && this.objectId.equals(rhs.objectId))))
        && ((this.objectType == rhs.objectType)
            || ((this.objectType != null) && this.objectType.equals(rhs.objectType))));
  }
}
