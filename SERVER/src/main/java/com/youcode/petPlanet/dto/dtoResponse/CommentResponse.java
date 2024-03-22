package com.youcode.petPlanet.dto.dtoResponse;

import com.youcode.petPlanet.dto.dtoRequest.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponse {
    private String body;
    private UserRequest user;
}
