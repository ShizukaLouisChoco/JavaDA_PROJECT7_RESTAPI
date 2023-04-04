package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleFormDto {

	@NotBlank(message="Name is mandatory")
	private String name;
	@NotBlank(message="Description is mandatory")
	private String description;
	@NotBlank(message="Json is mandatory")
	private String json;
	@NotBlank(message="Template is mandatory")
	private String template;
	@NotBlank(message="SqlStr is mandatory")
	private String sqlStr;
	@NotBlank(message="SqlPart is mandatory")
	private String sqlPart;
}
