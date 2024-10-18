package com.samanvay.admin.entity.DTO;

import com.samanvay.admin.entity.Address;

import io.micrometer.common.lang.Nullable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String name;
  private String email;
  private String phoneNumber;
  private String dateOfBirth;
  private Address address;

  private Boolean isActive;

  @Nullable
  private Long roleId;
  
  @Nullable
  private Long mandalId;
  
  @Nullable
  private UserReferenceDTO referenceContacts;
}
