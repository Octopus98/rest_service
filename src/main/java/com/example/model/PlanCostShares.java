package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"deductible", "_org", "copay", "objectId", "objectType"})
@Generated("jsonschema2pojo")
public class PlanCostShares {
  /** (Required) */
  @JsonProperty("deductible")
  private int deductible;
  /** (Required) */
  @JsonProperty("_org")
  private String _org;
  /** (Required) */
  @JsonProperty("copay")
  private int copay;
  /** (Required) */
  @JsonProperty("objectId")
  private String objectId;
  /** (Required) */
  @JsonProperty("objectType")
  private String objectType;

  /** No args constructor for use in serialization */
  public PlanCostShares() {}

  /**
   * @param org organzation
   * @param deductible deductible
   * @param copay copay
   * @param objectId object id
   * @param objectType object type
   */
  public PlanCostShares(int deductible, String org, int copay, String objectId, String objectType) {
    super();
    this.deductible = deductible;
    this._org = org;
    this.copay = copay;
    this.objectId = objectId;
    this.objectType = objectType;
  }

  /** (Required) */
  @JsonProperty("deductible")
  public int getDeductible() {
    return deductible;
  }

  /** (Required) */
  @JsonProperty("deductible")
  public void setDeductible(int deductible) {
    this.deductible = deductible;
  }

  public PlanCostShares withDeductible(int deductible) {
    this.deductible = deductible;
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

  public PlanCostShares withOrg(String org) {
    this._org = org;
    return this;
  }

  /** (Required) */
  @JsonProperty("copay")
  public int getCopay() {
    return copay;
  }

  /** (Required) */
  @JsonProperty("copay")
  public void setCopay(int copay) {
    this.copay = copay;
  }

  public PlanCostShares withCopay(int copay) {
    this.copay = copay;
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

  public PlanCostShares withObjectId(String objectId) {
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

  public PlanCostShares withObjectType(String objectType) {
    this.objectType = objectType;
    return this;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = ((result * 31) + this.copay);
    result = ((result * 31) + ((this._org == null) ? 0 : this._org.hashCode()));
    result = ((result * 31) + ((this.objectId == null) ? 0 : this.objectId.hashCode()));
    result = ((result * 31) + this.deductible);
    result = ((result * 31) + ((this.objectType == null) ? 0 : this.objectType.hashCode()));

    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof PlanCostShares) == false) {
      return false;
    }
    PlanCostShares rhs = ((PlanCostShares) other);
    return (((((this.copay == rhs.copay)
                    && ((this._org == rhs._org)
                        || ((this._org != null) && this._org.equals(rhs._org))))
                && ((this.objectId == rhs.objectId)
                    || ((this.objectId != null) && this.objectId.equals(rhs.objectId))))
            && (this.deductible == rhs.deductible))
        && ((this.objectType == rhs.objectType)
            || ((this.objectType != null) && this.objectType.equals(rhs.objectType))));
  }
}
