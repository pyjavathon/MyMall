package org.zerock.apiserver.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;

    //순번
    private int ord;

    public void setOrd(int ord){
        this.ord = ord;
    }
}
