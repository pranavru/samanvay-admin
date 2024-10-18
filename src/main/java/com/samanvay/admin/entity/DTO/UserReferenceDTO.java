package com.samanvay.admin.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReferenceDTO {
  private String contactDescription;
  private Long primaryContactId;
  private Long secondaryContactId;
}
