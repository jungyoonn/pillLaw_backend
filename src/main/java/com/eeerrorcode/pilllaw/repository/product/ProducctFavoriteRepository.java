package com.eeerrorcode.pilllaw.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.id.ProductFavoriteId;
import com.eeerrorcode.pilllaw.entity.product.ProductFavorite;

@Repository
public interface ProducctFavoriteRepository extends JpaRepository<ProductFavorite, ProductFavoriteId>{
    
}
