package com.etnetera.hr.search;

import com.etnetera.hr.entity.JavaScriptFramework;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FrameworkSpecificationBuilder {

    private final List<SearchParam> params;

    public Specification<JavaScriptFramework> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<JavaScriptFramework>> specs = params.stream()
                .map(FrameworkSearchSpecification::new)
                .collect(Collectors.toList());

        Specification<JavaScriptFramework> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }

}
