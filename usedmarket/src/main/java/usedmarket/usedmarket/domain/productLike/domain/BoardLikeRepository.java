package usedmarket.usedmarket.domain.productLike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.member.domain.Member;

public interface BoardLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductAndMember(Product product, Member member);
    void deleteByProductAndMember(Product product, Member member);
}
