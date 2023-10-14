package com.example.pomodoroapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoroapp.ui.theme.PomodoroAppTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PomodoroApp()
                }
            }
        }
    }
}

val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            PomodoroTopBar()
        }

    ) { innerPadding ->
        PomodoroAppBody(
            context,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}

enum class PomodoroState {
    IDLE,
    FOCUS,
    FINISH_FOCUS,
    SHORT_BREAK,
}

@Composable
fun PomodoroCountdown(progress: Float, modifier: Modifier){
    Log.d(TAG, "Pomodoro Countdown progress: $progress")
    CircularProgressIndicator(
        progress = progress,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(300.dp),
        strokeWidth = 12.dp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroAppBody(context: Context, modifier: Modifier){
    var timerState by remember { mutableStateOf(PomodoroState.IDLE) }
    var progress by remember { mutableStateOf(1f) }
    var initialFocusTime by remember { mutableStateOf(25) }
    var initialShortBreakTime by remember { mutableStateOf(5) }
    var settingsEditable by remember { mutableStateOf(true) }

    val (mainText, actionButton) = when (timerState) {
        PomodoroState.IDLE -> R.string.idle_text to R.string.act_start_focus_text
        PomodoroState.FOCUS -> R.string.start_focus_text to R.string.act_stop_text
        PomodoroState.FINISH_FOCUS -> R.string.it_s_time_for_a_break to R.string.act_start_break_text
        PomodoroState.SHORT_BREAK -> R.string.short_break_text to R.string.act_stop_text
        else -> R.string.idle_text to R.string.act_start_focus_text
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = stringResource(mainText),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PomodoroCountdown(progress, modifier)
            Image(
                painter = painterResource(id = R.drawable.tomato1),
                contentDescription = null,
                modifier = Modifier
                    .size(172.dp)
            )
        }

        Button(onClick = {
            when (timerState){
                PomodoroState.IDLE -> {
                    settingsEditable = false
                    timerState = PomodoroState.FOCUS
                    startTimer(initialFocusTime, {
                        progress = it
                        Log.d(TAG, "Countdown on Circle: $progress")
                    }, {
                        showNotification(context, "Focus Completed", "Your focus time has completed.")
                        timerState = PomodoroState.FINISH_FOCUS
                        settingsEditable = true
                        progress = 1f
                    }, context)
                }
                PomodoroState.FOCUS, PomodoroState.SHORT_BREAK -> {
                    settingsEditable = true
                    stopTimer(){
                        progress = 1f
                        timerState = PomodoroState.IDLE
                    }
                }
                PomodoroState.FINISH_FOCUS -> {
                    settingsEditable = false
                    startTimer(initialShortBreakTime, {
                        progress = it
                    },  {
                        showNotification(context, "Break Completed", "Your break time has completed.")
                        timerState = PomodoroState.IDLE
                        settingsEditable = true
                        progress = 1f
                    }, context)
                }
                else -> {
                    settingsEditable = true
                    progress = 1f
                    stopTimer(){
                        timerState = PomodoroState.IDLE
                    }
                }
            }
        }) {
            Text(text = stringResource(actionButton))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.customize_your_minutes),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        OutlinedTextField(
                            enabled = settingsEditable,
                            value = initialFocusTime.toString(),
                            onValueChange = { initialFocusTime = it.toIntOrNull() ?: 0 },
                            label = { Text(stringResource(R.string.focus_text)) }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        OutlinedTextField(
                            enabled = settingsEditable,
                            value = initialShortBreakTime.toString(),
                            onValueChange = { initialShortBreakTime = it.toIntOrNull() ?: 0 },
                            label = { Text(stringResource(R.string.break_text)) }
                        )
                    }
                }
            }
        }
    }

    // Use LaunchedEffect to trigger the countdown and update progress
//    LaunchedEffect(timerState) {
//        if (timerState == PomodoroState.FOCUS || timerState == PomodoroState.FINISH_FOCUS) {
//            val totalTimeInSec = if (timerState == PomodoroState.FOCUS) initialFocusTime * 60 else initialShortBreakTime * 60
//            var remainingTimeInSec = totalTimeInSec
//
//            while (remainingTimeInSec > 0) {
//                val progressPercentage = (remainingTimeInSec.toFloat() / totalTimeInSec.toFloat()) * 100f
//                progress = progressPercentage
//                delay(1000)
//                remainingTimeInSec--
//            }
//        }
//    }
}

private var timerJob: Job? = null
private fun startTimer(
    totalTimeInMin: Int,
    updateProgressBar: (Float) -> Unit,
    onTimerFinish: () -> Unit,
    context: Context
) {
//    stopTimer(onTimerFinish)
    timerJob = CoroutineScope(Dispatchers.Default).launch {
        val totalTimeInSec = totalTimeInMin * 60
        var remainingTimeInSec = totalTimeInSec

        while (remainingTimeInSec > 0){
            Log.d(TAG, remainingTimeInSec.toString())
            val progressPercentage = (remainingTimeInSec.toFloat() / totalTimeInSec.toFloat())
            Log.d(TAG, progressPercentage.toString())
            updateProgressBar(progressPercentage)
            delay(1000)
            remainingTimeInSec--
        }
        timerJob = null
        showNotification(context, "Timer Completed", "Your timer has completed.")
        onTimerFinish()
    }
}

private fun stopTimer(
    onTimerFinish: ()-> Unit
){
    timerJob?.cancel()
    timerJob = null
    onTimerFinish()
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PomodoroPreview() {
    PomodoroAppTheme {
        PomodoroApp()
    }
}