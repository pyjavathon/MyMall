package org.zerock.apiserver.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.apiserver.domain.Product;
import org.zerock.apiserver.repository.search.ProductSearch;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> , ProductSearch {


    //상품과 이미지 테이블 각각에서 조회 하지 않고 상품 테이블만 조회할 수 있도록 처리 (강의 엔티티 crud 처리
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")

    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying//수정 및 삭제 작업 위해 붙임
    @Query("update Product p set p.delFlag = :delFlag where p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("delFlag")boolean flag);

    @Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
