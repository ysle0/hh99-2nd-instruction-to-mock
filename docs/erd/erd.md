```mermaid
erDiagram
%%  ----- ----- users ----- -----
    users ||--|| wallets: has
    users ||--o{ orders: places
    users }o--o{ users_to_discount_coupons: owns
    users {
        string id PK "uuid"
        datetime created_at
    }
%%  ----- ----- users ----- -----

%%  ----- ----- wallets ----- -----
    wallets {
        string id PK "uuid"
        string user_id FK "unique, uuid"
        int balance "현재 유저의 보유 잔액"
        datetime created_at
    }
%%  ----- ----- wallets ----- -----

%%  ----- ----- products ----- -----
    products }o--o{ orders_to_products: in_order
    products {
        string id PK "uuid"
        text name "상품의 이름"
        int price "상품의 가격"
        int quantity "현재 상품의 재고"
        datetime created_at
        datetime updated_at
    }
%%  ----- ----- products ----- -----

%%  ----- ----- orders ----- -----
    orders }o--o{ orders_to_discount_coupons: in_order
    orders ||--o{ orders_to_products: in_order
    orders {
        string id PK "uuid"
        string user_id FK "uuid"
        enum status "('PENDING', 'PAID', 'FAILED')"
        datetime created_at
    }
%%  ----- ----- orders ----- -----

%%  ----- ----- discount_coupons ----- -----
    discount_coupons }o--o{ users_to_discount_coupons: assigned_to
    discount_coupons }o--o{ orders_to_discount_coupons: applied_to
    discount_coupons {
        string id PK "uuid"
        enum type "('AMOUNT', 'PERCENT') 할인금액, 할인 퍼센티지"
        int quantity "현재 수량"
        int discount_value "할인 수치. type 과 합쳐서 사용."
        datetime created_at
        datetime updated_at
        datetime expired_at
    }
%%  ----- ----- discount_coupons ----- -----

%%  ----- ----- orders <-> products ----- -----
    orders_to_products {
        string id PK "uuid"
        string order_id FK "uuid"
        string product_id FK "uuid"
        int ordered_quantity "주문한 상품의 총 갯수"
        int price_at_purchase "구매 시점의 상품 가격"
        datetime created_at
        datetime updated_at
    }

%%  ----- ----- orders <-> discount_coupons ----- -----
    orders_to_discount_coupons {
        string id PK "uuid"
        string order_id FK "uuid"
        string discount_coupon_id FK "uuid"
        int discount_value_at_purchase "구매 시점의 할인 수치, discount_unit_at_purchase 와 함께 사용"
        int discount_unit_at_purchase "구매 시점의 할인 쿠폰의 단위"
        bool is_used "할인 쿠폰 사용의 여부"
        datetime created_at
        datetime updated_at
    }
%%  ----- ----- orders <-> discount_coupons ----- -----

%%  ----- ----- users <-> discount_coupons ----- -----
    users_to_discount_coupons {
        string id PK "uuid"
        string user_id FK "uuid, unique"
        string discount_coupon_id FK "uuid"
        int quantity "유저가 보유하고 있는 할인 쿠폰의 갯수"
        datetime created_at
        datetime updated_at
    }
%%  ----- ----- users <-> discount_coupons ----- -----
```