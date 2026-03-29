package com.goncha.umbrella.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.goncha.umbrella.entity.Role;

@Component
public class RoleToStringConverter implements Converter<Role, String> {

	@Override
	public String convert(Role role) {
		return String.valueOf(role.getId());
	}
}
