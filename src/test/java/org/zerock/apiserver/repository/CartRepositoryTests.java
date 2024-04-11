package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Cart;
import org.zerock.apiserver.domain.CartItem;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.Product;
import org.zerock.apiserver.dto.CartItemListDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Commit
    @Transactional
    @Test
    public void testInsertByProduct(){

        String email = "user1@aaa.com";
        Long pno = 6L;
        int qty = 3;

        //이메일 상품번호로 장바구니 아이템 확인 없으면 추가 있으면 수량 변경해서 저장

        CartItem cartItem =  cartItemRepository.getItemOfPno(email,pno);

        if(cartItem != null){
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return;
        }
        //사용자의 장바구니에 장바구니 아이템 만들어서 저장
        //장바구니 자체가 없을 수도 있음
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart = null;

        if(result.isEmpty()){
            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();

            cart = cartRepository.save(tempCart);
        }else{//장바구니는 있으나 해당 상품의 장바구니 아이템은 없는 경우

            cart = result.get();

        }

        Product product = Product.builder().pno(pno).build();
        cartItem = CartItem.builder().cart(cart).product(product).qty(qty).build();

        cartItemRepository.save(cartItem);
    }
    @Test
    public void testListOfMember(){
        String email = "user1@aaa.com";

        List<CartItemListDTO> cartItemListDTOList = cartItemRepository.getItemsOfCartDTOByEmail(email);

        for(CartItemListDTO dto: cartItemListDTOList){
            log.info(dto);
        }


    }

    @Test
    public void testUpdateByCino(){
        Long cino = 1L;
        int qty = 4;

        Optional<CartItem> result =  cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);
    }

    @Test
    public void testDeleteThenList(){
        Long cino = 2L;

        Long cno = cartItemRepository.getCartFromItem(cino);

        cartItemRepository.deleteById(cino);

        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for(CartItemListDTO dto : cartItemList){
            log.info(dto);
        }
    }
}
