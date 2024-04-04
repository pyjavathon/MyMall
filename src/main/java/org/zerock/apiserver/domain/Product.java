package org.zerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name="tbl_product")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();


    public void changePrice(int price){
        this.price = price;
    }

    public void changeDesc(String desc){
        this.pdesc = desc;
    }

    public void changeName(String name){
        this.pname = name;
    }

    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image){
        image.setOrd(imageList.size());
        imageList.add(image);
    }


    //파일 3개 중 2개는 쓰고 1개 수정 시 (예시
    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
                    .fileName(fileName)
                    .build();
        addImage(productImage);
    }

    public void clearList(){
        this.imageList.clear();
    }
}
