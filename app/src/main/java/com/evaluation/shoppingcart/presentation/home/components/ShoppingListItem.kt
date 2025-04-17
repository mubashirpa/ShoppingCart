package com.evaluation.shoppingcart.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.presentation.theme.ShoppingCartTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShoppingListItem(
    onClick: () -> Unit,
    item: ShoppingItem,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(text = item.itemName.orEmpty())
        },
        supportingContent = {
            Column {
                Text(text = stringResource(R.string.mrp, item.sellingPrice.toString()))
                Text(text = stringResource(R.string.tax_percentage, "${item.taxPercentage}%"))
            }
        },
        trailingContent = {
            val size = ButtonDefaults.ExtraSmallContainerHeight
            OutlinedButton(onClick = onAddToCartClick) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.iconSizeFor(size)),
                )
                Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
                Text(text = stringResource(R.string.add_to_cart))
            }
        },
        modifier = modifier.clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true)
@Composable
private fun ShoppingListItemPreview() {
    ShoppingCartTheme {
        ShoppingListItem(
            onClick = {},
            item =
                ShoppingItem(
                    itemName = "Item Name",
                    sellingPrice = 50.0,
                    taxPercentage = 5.0,
                ),
            onAddToCartClick = {},
        )
    }
}
