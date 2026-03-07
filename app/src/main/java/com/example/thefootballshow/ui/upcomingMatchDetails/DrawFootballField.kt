package com.example.thefootballshow.ui.upcomingMatchDetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.thefootballshow.R


@Composable
fun DrawFootballField(modifier: Modifier = Modifier) {
    val color: Color = colorResource(id = R.color.gray_level_3)
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val height = 800f
        val yLeftStart = 150f
        val smallYLeftStart = 250f
        val smallRectWidth = 120f
        val largeRectHeight = 200f


        drawParentRect(canvasWidth, height)
        drawPath(canvasWidth, height, color)
        drawPenaltyArea(yLeftStart, height)
        drawLeftPenaltyArc()
        drawLeftGoalArea(smallYLeftStart, smallRectWidth, height)


        drawCentreCircle(canvasWidth, height)
        drawCentreLine(canvasWidth, height)
        drawTextWithBackground(canvasWidth, height)


        drawRightPenaltyArea(canvasWidth, yLeftStart, height)
        drawRightGoalArea(canvasWidth, smallRectWidth, smallYLeftStart, height)

        drawRightPenaltyArc(canvasWidth)


    }

}


private fun DrawScope.drawRightPenaltyArc(canvasWidth: Float) {
    drawArc(
        color = Color.DarkGray,
        startAngle = 90f,
        sweepAngle = 180f,
        useCenter = false,
        style = Stroke(2.dp.toPx()),
        topLeft = Offset(canvasWidth - 290f, 300f),
        size = Size(150f, 150f),
    )
}


private fun DrawScope.drawRightPenaltyArea(
    canvasWidth: Float,
    yLeftStart: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(canvasWidth - 220f, yLeftStart),
        size = Size(220f, height - 2 * yLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawRightGoalArea(
    canvasWidth: Float,
    smallRectWidth: Float,
    smallYLeftStart: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(canvasWidth - smallRectWidth, smallYLeftStart),
        size = Size(smallRectWidth, height - 2 * smallYLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawTextWithBackground(
    canvasWidth: Float,
    height: Float
) {
    //Draw Text at centre
    val text = "MCI"
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 40f
        textAlign = android.graphics.Paint.Align.CENTER
    }

    // Measure the text's width and height for the background size
    val textWidth = textPaint.measureText(text)
    val textHeight = textPaint.fontMetrics.run { descent - ascent }
    val padding = 16f

    drawRect(
        color = Color.Yellow, // Background color for the text
        topLeft = Offset(
            canvasWidth / 2 - padding,
            height / 2 + textPaint.fontMetrics.ascent - padding
        ),
        size = Size(textWidth + 1.5f * padding, textHeight + 1.5f * padding)
    )

    drawContext.canvas.nativeCanvas.drawText(
        text,
        canvasWidth / 2 + 30f,
        height / 2,
        textPaint
    )

    val text2 = "Attack"
    val text2Width = textPaint.measureText(text2)
    val text2Height = textPaint.fontMetrics.run { descent - ascent }

    val y2 = height / 2 + textHeight + padding
    drawContext.canvas.nativeCanvas.drawText(
        text2,
        canvasWidth / 2 - 5f,
        y2,
        textPaint
    )
}


private fun DrawScope.drawCentreLine(
    canvasWidth: Float,
    height: Float
) {
    //Draw Line at Centre
    drawLine(
        color = Color.DarkGray,
        start = Offset(canvasWidth / 2, 0f),
        end = Offset(canvasWidth / 2, height),
        strokeWidth = 2.dp.toPx()
    )
}


private fun DrawScope.drawCentreCircle(
    canvasWidth: Float,
    height: Float
) {
    //Draw Circle at Centre
    drawCircle(
        color = Color.DarkGray,
        radius = 100f,
        center = Offset(canvasWidth / 2, height / 2),
        style = Stroke(2.dp.toPx())
    )
}

private fun DrawScope.drawLeftPenaltyArc() {

    //0 Degree means = 3'0 Clock
    //90 Degree means = 6'0 Clock
    //180 Degree means = 9'0 Clock
    //270 Degree means = 12'0 Clock
    //-180 Degree means = 12'0 Counter Clock

    drawArc(
        color = Color.DarkGray,
        startAngle = 270f,
        sweepAngle = 180f,
        useCenter = false,
        style = Stroke(2.dp.toPx()),
        topLeft = Offset(140f, 300f),
        size = Size(150f, 150f),
    )
}


private fun DrawScope.drawPenaltyArea(
    yLeftStart: Float,
    height: Float
) {
    //if we start from 100f then we also stop from same padding before full height
    //800, 100f, 800f + 100f = 900 - 2*100f = 700

    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, yLeftStart),
        size = Size(220f, height - 2 * yLeftStart),
        style = Stroke(2.dp.toPx())
    )
}

private fun DrawScope.drawLeftGoalArea(
    smallYLeftStart: Float,
    smallRectWidth: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, smallYLeftStart),
        size = Size(smallRectWidth, height - 2 * smallYLeftStart),
        style = Stroke(2.dp.toPx())
    )
}


private fun DrawScope.drawPath(
    canvasWidth: Float,
    height: Float,
    color: Color
) {
    val path = Path().apply {
        // Start from a point
        moveTo(0f, 0f)
        lineTo(canvasWidth / 2 + 120f, 0f)
        lineTo(canvasWidth / 2 + 180f, 100f)
        lineTo(canvasWidth / 2 + 120f, 200f)
        lineTo(canvasWidth / 2 + 180f, 300f)
        lineTo(canvasWidth / 2 + 120f, 400f)
        lineTo(canvasWidth / 2 + 180f, 500f)
        lineTo(canvasWidth / 2 + 120f, 600f)
        lineTo(canvasWidth / 2 + 180f, 700f)
        lineTo(canvasWidth / 2 + 120f, 800f)
        lineTo(0f, height)
        // close()
    }

    drawPath(
        path = path,
        color = color // Background color up to the path
    )

    drawPath(
        path = path,
        color = Color.Blue,
        style = Stroke(width = 2.dp.toPx()) // Set the stroke width
    )
}


private fun DrawScope.drawParentRect(
    canvasWidth: Float,
    height: Float
) {
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset(0f, 0f),
        size = Size(canvasWidth, height),
        style = Stroke(4.dp.toPx())
    )
}