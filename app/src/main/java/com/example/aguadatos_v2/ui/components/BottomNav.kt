package com.example.aguadatos_v2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentScreen: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavButton("Home", R.drawable.home, onHomeClick, currentScreen == "Home")
        BottomNavButton("Records", R.drawable.records, onRecordsClick, currentScreen == "Records")
        BottomNavButton("Graph", R.drawable.graph, onGraphsClick, currentScreen == "Graph")
        BottomNavButton("Profile", R.drawable.person, onProfileClick, currentScreen == "Profile")
    }
}

@Composable
fun BottomNavButton(text: String, iconRes: Int, onClick: () -> Unit, isSelected: Boolean = false) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
                .padding(bottom = 2.dp),
            colorFilter = if (isSelected) ColorFilter.tint(Color(0xff3c89e1)) else null
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xff3c89e1) else Color.Black
        )
    }
}