package com.etnetera.hr.search;

import com.etnetera.hr.entity.JavaScriptFramework;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Implementation taken from https://www.baeldung.com/rest-api-search-language-spring-data-specifications
 */
@Slf4j
@AllArgsConstructor
public class FrameworkSearchSpecification implements Specification<JavaScriptFramework> {

    private final SearchParam searchParam;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Predicate toPredicate
            (Root<JavaScriptFramework> root, CriteriaQuery<?> query, CriteriaBuilder builder) throws UnsupportedOperationException {

        if (searchParam.getOperation().equalsIgnoreCase(">")) {
            try {
                var localDate = LocalDate.parse((CharSequence) searchParam.getValue(), dateTimeFormatter);
                return builder.greaterThanOrEqualTo(
                        root.get(searchParam.getParamName()), localDate);
            } catch (Exception e) {
                  log.error("Cannot parse given string to LocalDate.");
                  throw new UnsupportedOperationException("> operation allowed for dates in `yyyy-MM-dd` format.");
            }
        }
        else if (searchParam.getOperation().equalsIgnoreCase("<")) {
            try {
                var localDate = LocalDate.parse((CharSequence) searchParam.getValue(), dateTimeFormatter);
                return builder.lessThanOrEqualTo(
                        root.get(searchParam.getParamName()), localDate);
            } catch (Exception e) {
                log.error("Cannot parse given string to LocalDate.");
                throw new UnsupportedOperationException("> operation allowed for dates in `yyyy-MM-dd` format.");
            }
        }
        else if (searchParam.getOperation().equalsIgnoreCase(":")) {
            if (root.get(searchParam.getParamName()).getJavaType() == String.class) {
                return builder.like(
                        root.get(searchParam.getParamName()), "%" + searchParam.getValue() + "%");
            } else {
                return builder.equal(root.get(searchParam.getParamName()), searchParam.getValue());
            }
        }
        return null;
    }
}
