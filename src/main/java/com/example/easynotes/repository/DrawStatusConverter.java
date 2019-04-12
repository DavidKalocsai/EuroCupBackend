package com.example.easynotes.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.easynotes.model.DrawStatus;

@Converter(autoApply = true)
public class DrawStatusConverter implements AttributeConverter<DrawStatus, String> {
	@Override
	public String convertToDatabaseColumn(final DrawStatus attribute) {
		final DrawStatus drawStatus = setDefaultIfNull(attribute);
		return drawStatus.getCode();
	}

	private DrawStatus setDefaultIfNull(final DrawStatus attribute) {
		DrawStatus retDrawStatus = attribute;
		if (retDrawStatus == null) {
			retDrawStatus = DrawStatus.NO_DRAW; 	
		}
		return retDrawStatus;
	}

	@Override
	public DrawStatus convertToEntityAttribute(final String dbData) {
		return DrawStatus.getEnumFromCode(dbData);
	}
}
