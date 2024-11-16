package com.samanvay.admin.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {
  private String username;
  private String password;
  private String email;
}
