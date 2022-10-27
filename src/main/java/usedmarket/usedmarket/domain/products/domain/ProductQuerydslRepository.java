package usedmarket.usedmarket.domain.products.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static usedmarket.usedmarket.domain.products.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Product> getProductByCreatedDate() {
        return queryFactory
                .selectFrom(product)
                .where(product.productStatus.eq(ProductStatus.COMPLETE).not())
                .limit(20)
                .orderBy(product.createdDate.desc())
                .fetch();
    }

    public List<Product> getProductAllByCreatedDate(Long productId, Pageable pageable) {
        return queryFactory
                .selectFrom(product)
                .where(product.productStatus.eq(ProductStatus.COMPLETE).not())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdDate.desc())
                .where(product.id.lt(productId))
                .fetch();
    }

    public List<Product> getProductByCategoryAndOrder(Category category, String order, Pageable pageable) {
        return queryFactory
                .selectFrom(product)
                .where(product.category.eq(category).and(product.productStatus.eq(ProductStatus.COMPLETE).not()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortProduct(order))
                .fetch();
    }

    private OrderSpecifier<?> sortProduct(String keyword) {
        if(!keyword.isEmpty()) {
            switch (keyword) {
                case "date" :
                    return product.createdDate.desc();
                case "popular" :
                    return product.productLikeList.size().desc();
                case "price_asc" :
                    return product.price.asc();
                case "price_desc" :
                    return product.price.desc();
            }
        }
        return product.createdDate.desc(); //default 응답
    }

}
