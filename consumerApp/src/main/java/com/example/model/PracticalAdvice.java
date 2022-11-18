package com.example.model;

import javax.validation.constraints.NotBlank;

public record PracticalAdvice(@NotBlank String message,long identifier) {

}
