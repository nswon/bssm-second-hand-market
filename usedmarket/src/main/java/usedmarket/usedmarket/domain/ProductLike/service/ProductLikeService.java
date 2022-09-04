package usedmarket.usedmarket.domain.ProductLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usedmarket.usedmarket.domain.ProductLike.domain.ProductLike;
import usedmarket.usedmarket.domain.ProductLike.domain.BoardLikeRepository;
import usedmarket.usedmarket.domain.products.domain.Product;
import usedmarket.usedmarket.domain.products.domain.ProductsRepository;
import usedmarket.usedmarket.domain.member.domain.Member;
import usedmarket.usedmarket.domain.member.domain.MemberRepository;
import usedmarket.usedmarket.global.jwt.SecurityUtil;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final ProductsRepository productsRepository;
    private final MemberRepository memberRepository;

    public void like(Long id) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));

        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 후 이용해주세요."));

        if(boardLikeRepository.existsByProductAndMember(product, member)) {
            boardLikeRepository.deleteByProductAndMember(product, member);
        }
        else {
            ProductLike productLike = ProductLike.builder()
                    .product(product)
                    .member(member)
                    .build();

            productLike.confirmBoard(product);
            productLike.confirmMember(member);

            boardLikeRepository.save(productLike);
        }
    }
}
