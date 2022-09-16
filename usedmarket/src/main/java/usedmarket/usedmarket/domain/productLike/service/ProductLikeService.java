package usedmarket.usedmarket.domain.productLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.productLike.domain.ProductLike;
import usedmarket.usedmarket.domain.productLike.domain.ProductLikeRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductsRepository productsRepository;
    private final MemberRepository memberRepository;

    public void like(Long productId) {
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(productLikeRepository.existsByProductAndMember(product, member)) {
            productLikeRepository.deleteByProductAndMember(product, member);
        }
        else {
            ProductLike productLike = ProductLike.builder()
                    .product(product)
                    .member(member)
                    .build();

            productLike.confirmProduct(product);
            productLike.confirmMember(member);

            productLikeRepository.save(productLike);
        }
    }
}
