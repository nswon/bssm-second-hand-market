package usedmarket.usedmarket.domain.productLike.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import usedmarket.usedmarket.domain.member.domain.Member;

import java.util.List;
import static usedmarket.usedmarket.domain.productLike.domain.QProductLike.productLike;
import static usedmarket.usedmarket.domain.products.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductLikeQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<ProductLike> getProductLikeByMember(Member member, String order) {
        return queryFactory
                .selectFrom(productLike)
                .where(productLike.member.eq(member))
                .orderBy(sortProductLike(order))
                .fetch();
    }

    private OrderSpecifier<?> sortProductLike(String keyword) {
        if(!keyword.isEmpty()) {
            switch (keyword) {
                case "date" :
                    return productLike.product.createdDate.desc();
                case "popular" :
                    return productLike.product.productLikeList.size().desc();
                case "price_asc" :
                    return productLike.product.price.asc();
                case "price_desc" :
                    return productLike.product.price.desc();
            }
        }
        return productLike.product.createdDate.desc(); //default 응답
    }

}
