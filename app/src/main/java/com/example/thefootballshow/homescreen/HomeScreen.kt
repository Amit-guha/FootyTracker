package com.example.thefootballshow.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thefootballshow.R
import com.example.thefootballshow.data.model.AreaCompetition
import com.example.thefootballshow.ui.base.UiState
import com.example.thefootballshow.ui.commonui.MatchTitle
import com.example.thefootballshow.utils.extension.Dimens

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val topLeagues: UiState<List<AreaCompetition>> by viewModel.topLeagues.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        TopLeagues(topLeagues){
            viewModel.selectedLeague(it)
        }
        MatchTitle(
            title = stringResource(R.string.live_matches),
            modifier = Modifier.padding(start = Dimens.PaddingMedium)
        )
        LiveMatches()
        MatchTitle(
            title = stringResource(R.string.upcoming_matches),
            modifier = Modifier.padding(
                start = Dimens.PaddingMedium,
                top = Dimens.dp_14
            )
        )
        UpcomingMatchList()
    }

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}