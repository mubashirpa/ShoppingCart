package com.evaluation.shoppingcart.presentation.home.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(text = item.itemName.orEmpty())
        },
        modifier = modifier,
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}
