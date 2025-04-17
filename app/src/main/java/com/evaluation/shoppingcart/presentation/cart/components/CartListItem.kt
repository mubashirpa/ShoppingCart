package com.evaluation.shoppingcart.presentation.cart.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.presentation.theme.ShoppingCartTheme

@Composable
fun CartListItem(
    item: ShoppingItem,
    addedToCart: Boolean,
    onAddToCartClick: (addedToCart: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(text = item.itemName.orEmpty())
        },
        trailingContent = {
            IconToggleButton(checked = addedToCart, onCheckedChange = onAddToCartClick) {
                if (addedToCart) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                    )
                } else {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                    )
                }
            }
        },
        modifier = modifier,
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true)
@Composable
private fun ShoppingListItemPreview() {
    ShoppingCartTheme {
        CartListItem(
            item =
                ShoppingItem(
                    itemName = "Item Name",
                ),
            addedToCart = true,
            onAddToCartClick = {},
        )
    }
}
