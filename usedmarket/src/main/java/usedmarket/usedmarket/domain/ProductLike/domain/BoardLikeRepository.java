package usedmarket.usedmarket.domain.ProductLike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.member.domain.Member;

public interface BoardLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductAndMember(Product product, Member member); //좋아요를 이미 눌렀다면 true
    void deleteByProductAndMember(Product product, Member member); //좋아요 삭제
}
