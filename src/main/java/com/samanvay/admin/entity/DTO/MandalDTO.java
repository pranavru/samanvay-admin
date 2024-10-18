package com.samanvay.admin.entity.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MandalDTO {
  private Long id;
  private String name;
  private String location;
  private Long zoneId;
}
