package com.etnetera.hr.entity;

import com.etnetera.hr.dto.HypeLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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

//    @Column
//    private String version; // TODO

    @Column
    @Temporal(TemporalType.DATE)
    private Date deprecationDate;

    @Column
    @Enumerated(value = EnumType.STRING)
    private HypeLevel hypeLevel;
}
