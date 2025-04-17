package com.evaluation.shoppingcart.presentation.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.presentation.theme.ShoppingCartTheme

@Composable
fun CartListItem(
    onClick: () -> Unit,
    item: ShoppingItem,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(text = item.itemName.orEmpty())
        },
        supportingContent = {
            Text(text = stringResource(R.string.mrp, item.sellingPrice.toString()))
        },
        trailingContent = {
            val quantity = item.quantity ?: 0
            Text(text = pluralStringResource(R.plurals.item_count, quantity, quantity))
        },
        modifier = modifier.clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true)
@Composable
private fun ShoppingListItemPreview() {
    ShoppingCartTheme {
        CartListItem(
            onClick = {},
            item =
                ShoppingItem(
                    itemName = "Item Name",
                    sellingPrice = 50.0,
                    quantity = 5,
                ),
        )
    }
}
