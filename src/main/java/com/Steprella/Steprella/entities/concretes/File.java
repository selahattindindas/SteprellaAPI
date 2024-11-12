package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="files")
@Inheritance(strategy = InheritanceType.JOINED)
public class File extends BaseEntity {

    @JoinColumn(name="file_name")
    private String imageName;

    @JoinColumn(name = "path")
    private String path;
}
