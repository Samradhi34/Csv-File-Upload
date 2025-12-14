package com.project.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.project.dto.UserRequestDTO;
import com.project.entity.User;


@Component
public class UserMapper {
	
	public static User dtoToEntity(UserRequestDTO dto) {
		
		if(dto==null) return null;
		
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static UserRequestDTO entityToDto(User entity) {
    	
    	if(entity==null) return null;
    	
        UserRequestDTO dto = new UserRequestDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<UserRequestDTO> toDtos(List<User> users) {
        return users.stream().map(UserMapper::entityToDto).collect(Collectors.toList());
    }
}
