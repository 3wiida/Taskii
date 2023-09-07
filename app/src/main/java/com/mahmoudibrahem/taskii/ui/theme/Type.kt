package com.mahmoudibrahem.taskii.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mahmoudibrahem.taskii.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val SfDisplay = FontFamily(
    Font(resId = R.font.sf_pro_display_bold, weight = FontWeight.Bold),
    Font(resId = R.font.sf_pro_display_regular, weight = FontWeight.Normal),
    Font(resId = R.font.sf_pro_display_medium, weight = FontWeight.Medium),
)

val SfText = FontFamily(
    Font(resId = R.font.sf_pro_text_bold, weight = FontWeight.Bold),
    Font(resId = R.font.sf_pro_text_regular, weight = FontWeight.Normal),
    Font(resId = R.font.sf_pro_text_medium, weight = FontWeight.Medium),
    Font(resId = R.font.sf_pro_text_semibold, weight = FontWeight.SemiBold)
)