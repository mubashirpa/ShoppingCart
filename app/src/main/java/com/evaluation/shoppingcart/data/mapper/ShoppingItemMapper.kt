package com.evaluation.shoppingcart.data.mapper

import com.evaluation.shoppingcart.data.remote.dto.shoppingItems.ShoppingItemDto
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

fun ShoppingItemDto.toShoppingItem(): ShoppingItem =
    ShoppingItem(
        itemID = itemID,
        itemName = itemName,
        sellingPrice = sellingPrice,
        taxPercentage = taxPercentage,
    )
