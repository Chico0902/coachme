package com.ssafy.db.entity.type;

public enum CategoryType {
  MAIN("MAIN"), SUB("SUB");
  private String CategoryTypeCode;

  CategoryType(String CategoryTypeCode) {
    this.CategoryTypeCode = CategoryTypeCode;
  }
}
