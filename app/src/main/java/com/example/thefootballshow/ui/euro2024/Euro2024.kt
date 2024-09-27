package com.example.thefootballshow.ui.euro2024

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.EuroSortingModel
import com.example.thefootballshow.data.model.Matche

@Composable
fun MainScreenEuro(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        EuroHeader(modifier = modifier)
        FilterSortingTitle(modifier = modifier)
    }
}


@Composable
fun EuroHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = modifier,
            shape = CircleShape,
        ) {
            Icon(
                modifier = modifier
                    .height(44.dp)
                    .width(44.dp),
                painter = painterResource(id = R.drawable.pl_main_logo),
                contentDescription = null
            )
        }


        Text(
            text = "European Championship",
            modifier = modifier
                .padding(start = 10.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FilterSortingTitle(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(getFilterText()){
            FilterText(it.title)
        }

    }
}

@Composable
fun FilterText(title : String,modifier: Modifier = Modifier) {
    Text(
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp,
        modifier = modifier.padding(top = 8.dp, start = 8.dp)
    )
}


fun getFilterText(): MutableList<EuroSortingModel> {
    val dataList = mutableListOf<EuroSortingModel>()
    dataList.add(EuroSortingModel(isSelected = true, title = "All"))
    dataList.add(EuroSortingModel(isSelected = false, title = "Group"))
    dataList.add(EuroSortingModel(isSelected = false, title = "Group Point"))

    return dataList
}


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreenEuro()
}