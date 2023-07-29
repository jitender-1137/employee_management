package com.universal.em.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.universal.em.exception.ServiceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    private static final String URL_REGEX = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))"
            + "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" + "([).!';/?:,][[:blank:]])?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private static final String GMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@gmail.com";

    private static final Pattern GMAIL_PATTERN = Pattern.compile(GMAIL_REGEX);

    private static final String MAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern MAIL_PATTERN = Pattern.compile(MAIL_REGEX);

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
    }

    public static <S, T> T convert(S data, Class<T> targetClass) {
        return objectMapper.convertValue(data, targetClass);
    }

    public static String convertToString(Object data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            return json;
        } catch (JsonProcessingException e) {
            throw new ServiceException("CS_00");
        }
    }

    public static <T> T convertToObject(String data, Class<T> targetClass) {
        try {
            return objectMapper.readValue(data, targetClass);
        } catch (JsonProcessingException e) {
            throw new ServiceException("CS_00");
        }
    }

    public static <S, T> String convertObjectToString(S data, Class<T> targetClass) {
        T t = convert(data, targetClass);
        String json = convertToString(t);
        return json;
    }

    public static boolean isValidUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    public static boolean isValidGmail(String gmail) {
        Matcher matcher = GMAIL_PATTERN.matcher(gmail);
        return matcher.matches();
    }

    public static boolean isValidMail(String mail) {
        Matcher matcher = MAIL_PATTERN.matcher(mail);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    public static String removeWhiteSpace(String input) {
        return toSmallerCase(input.replaceAll("\\s", ""));
    }

    public static String toSmallerCase(String input) {
        return input.toLowerCase();
    }

    public static boolean isTxtFile(String fileName) {
        String[] fileNameArray = fileName.split("\\.");
        String extention = fileNameArray[fileNameArray.length - 1];
        return extention.equals("txt");
    }

//	public static GenericSearchFilter getGenericSearchFilterWithAllEqualsComparison(Map<String, Object> criteriaMap) {
//		Map<String, ComparativeRelationAndValue> searchParams = new HashMap();
//		GenericSearchFilter genericSearchFilter = new GenericSearchFilter();
//		for (String curKey : criteriaMap.keySet()) {
//			Object curValue = criteriaMap.get(curKey);
//			ComparativeRelationAndValue curComparativeRelationAndValue = new ComparativeRelationAndValue();
//			curComparativeRelationAndValue.setComparativeRelation(ComparativeRelation.eq);
//			curComparativeRelationAndValue.setValue(curValue);
//			searchParams.put(curKey, curComparativeRelationAndValue);
//		}
//		genericSearchFilter.setSearchParams(searchParams);
//		return genericSearchFilter;
//	}
//
//	public static GenericSearchFilter getGenericSearchFilterWithDifferentComparison(
//			Map<String, ComparativeRelationAndValue> criteriaMap) {
//		GenericSearchFilter genericSearchFilter = new GenericSearchFilter();
//		genericSearchFilter.setSearchParams(criteriaMap);
//		return genericSearchFilter;
//	}
}
