package com.linh.titledeed.presentation.ui.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.titledeed.R

@Composable
fun ColumnScope.PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    val passwordVisible = remember { mutableStateOf(false) }

    TextField(
        value,
        onValueChange,
        modifier,
        enabled,
        readOnly,
        textStyle,
        label,
        placeholder,
        leadingIcon,
        trailingIcon = {
            val icon = if (passwordVisible.value) {
                R.drawable.ic_password_visible_24
            } else {
                R.drawable.ic_password_hidden_24
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = painterResource(icon), contentDescription = null)
            }
        },
        isError = !errorText.isNullOrBlank(),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions,
        keyboardActions,
        singleLine,
        maxLines,
        interactionSource,
        shape,
        colors
    )

    if (errorText != null) {
        Text(errorText, Modifier.padding(start = 16.dp, top = 4.dp), color = MaterialTheme.colors.error)
    }
}

@Preview
@Composable
fun PasswordTextFieldErrorPreview() {
    Column {
        PasswordTextField("", {}, errorText = "Password does not match!")
    }
}