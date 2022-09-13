package usedmarket.usedmarket.domain.products.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import usedmarket.usedmarket.domain.member.presentation.dto.request.MemberProductRequestDto;
import usedmarket.usedmarket.domain.notification.presentation.response.NotificationResponseDto;

import java.time.LocalDateTime;
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

    public List<Product> getProductsBySort(MemberProductRequestDto requestDto) {
        return queryFactory
                .selectFrom(product)
                .orderBy(sortProduct(requestDto.getKeyword()))
                .fetch();
    }

    private OrderSpecifier<?> sortProduct(String keyword) {
        if(!keyword.isEmpty()) {
            switch (keyword) {
                case "최신순" :
                    return product.createdDate.desc();
                case "좋아요순" :
                    return product.productLikeList.size().desc();
                case "저가순" :
                    return product.price.asc();
                case "고가순" :
                    return product.price.desc();
            }
        }
        return product.createdDate.desc(); //default 응답
    }
}
