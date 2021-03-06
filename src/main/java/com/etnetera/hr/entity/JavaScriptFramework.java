package com.etnetera.hr.entity;

import com.etnetera.hr.dto.HypeLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera, Petr Kadlec
 *
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class JavaScriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull
    @Column(nullable = false, length = 30)
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="versions", joinColumns = @JoinColumn(name="framework_id"))
    @Column
    private Set<String> version = new HashSet<>();

    @Column
    private LocalDate deprecationDate;

    @Column
    @Enumerated(value = EnumType.STRING)
    private HypeLevel hypeLevel;
}
