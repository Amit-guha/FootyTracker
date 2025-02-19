package com.example.thefootballshow.ui.leagueSelector

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.utils.extension.loadAsyncImage

@Composable
fun LeagueSelectorItem(areaCompetition: AreaCompetition, onClick: (leagueId: Int) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(if (areaCompetition.isSelected) R.color.pink_500 else R.color.white),
            contentColor = Color.Gray
        ),
        onClick = {
            areaCompetition.id?.let {
                onClick(it)
            }
        },
        modifier = Modifier.padding(end = 10.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            areaCompetition.emblem?.takeIf { it.isNotEmpty() }?.let {
                Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .loadAsyncImage(
                        url = it,
                        context = LocalContext.current,
                        contentDescription = "Away Team Logo"
                    )()
            }?:
            Image(
                painter = painterResource(id = R.drawable.premier_league_logo),
                contentDescription = "Premier League Logo",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = areaCompetition.name?:"",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = if (areaCompetition.isSelected) Color.White else Color.Black,
                    textAlign = TextAlign.Start
                )

            )
        }


    }


}

@Composable
@Preview(showBackground = true)
fun LeagueSelectorPreview(modifier: Modifier = Modifier) {
}