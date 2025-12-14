package com.project.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of Message By Locale Service
 *
 * 
 *
 */
@Component
@RequiredArgsConstructor
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

	private final MessageSource messageSource;

	/**
	 * Get localized message using LocaleContextHolder
	 */
	@Override
	public String getMessage(final String id, final Object[] arg) {
		final Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, arg, locale);
	}
}

