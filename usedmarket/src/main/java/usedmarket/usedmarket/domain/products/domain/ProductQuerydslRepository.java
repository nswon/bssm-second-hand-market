package usedmarket.usedmarket.domain.products.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import usedmarket.usedmarket.domain.notification.presentation.response.NotificationResponseDto;

import java.util.List;
import static usedmarket.usedmarket.domain.products.domain.QProduct.product;
import static usedmarket.usedmarket.domain.notification.domain.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<NotificationResponseDto> getProductsByKeywordAndCreatedDate(String keyword) {
        return queryFactory
                .select(Projections.constructor(NotificationResponseDto.class,
                        product.imgPath,
                        notification.keyword,
                        product.title,
                        product.createdDate))
                .from(product)
                .join(notification).on(product.id.eq(notification.id))
                .where(product.title.contains(keyword).and(product.createdDate.after(notification.createdDate)))
                .fetch();
    }

    public List<Product> getProductsByKeywordSort(String order) {
        return queryFactory
                .selectFrom(product)
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
