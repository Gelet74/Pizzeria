package com.example.pizzeria.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pizzeria.R


val MiFuenteFamilia = FontFamily(

    Font(R.font.inter, FontWeight.Normal)
)



val AppTypography = Typography(

    bodyLarge = TextStyle(
        fontFamily = MiFuenteFamilia,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),


    headlineLarge = TextStyle(
        fontFamily = MiFuenteFamilia,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    )


)