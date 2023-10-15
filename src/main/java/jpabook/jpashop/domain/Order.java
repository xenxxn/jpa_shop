package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne // 연관관계 매핑 - 다대일
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    //예시일 뿐, 잘못된 코드
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}


/*
연관관계 매핑시 고려사항
1. 다중성
    다대일: @ManyToOne
    일대다: @OneToMany
    일대일: @OneToOne
    다대다: @ManyToMany

    * @ManyToMany 는 실무에서 쓰면 안됨
        why?

2. 단방향, 양방향
    테이블
        -외래 키 하나로 양쪽 조인 가능
        -사실 방향이라는 개념은 없음.

    객체
        -참조용 필드가 있는 쪽으로만 참조 가능
        -한쪽만 참조하면 단방향
        -양쪽이 서로 참조하면 양방향
            * 참조입장에서 보면 단방향이 두개 있는것. 양쪽으로 거니까 양방향인 것처럼 보이는 것 뿐

3. 연관관계의 주인
    -테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
    -객체 양방향 관계는 A -> B, B -> A 처럼 참조가 2군데
    -객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래키를 관리할 곳을 지정해야함.
    -연관관계의 주인 : 외래키를 관리하는 참조
    -주인의 반대편: 외래키에 영향을 주지 않고 단순 조회만 가능


관계형 DB에서는 항상 '다' 쪽에 외래키가 들어가야 올바른 설계를 할 수 있음.
 */