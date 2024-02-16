package com.ssafy.db.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum Privilege {
  COACH("COACH"), COAME("COAME"), ADMIN("ADMIN");

  private String privilegeCode;

  Privilege(String privilegeCode) {
    this.privilegeCode = privilegeCode;
  }

  @JsonValue
  public String getPrivilegeCode() {
    return this.privilegeCode;
  }
}
