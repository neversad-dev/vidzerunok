package com.neversad.vidzerunok.feature.editor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.CanvasControls(
    onRectangleClick: () -> Unit,
    onCircleClick: () -> Unit,
    onArrowClick: () -> Unit,
    onClearCanvas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterHorizontally
        )
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onRectangleClick()
                }
                .drawBehind {
                    drawRect(
                        color = Color.Black,
                        style = Stroke(width = 5f)
                    )
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onCircleClick()
                }
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        style = Stroke(width = 5f)
                    )
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onArrowClick()
                }
                .drawBehind {

                        // Draw the arrow shaft
                        drawLine(
                            color = Color.Black,
                            start = Offset(size.width * 0.2f, size.height * 0.5f),
                            end = Offset(size.width * 0.8f, size.height * 0.5f),
                            strokeWidth = 5f
                        )

                        // Draw the arrowhead
                        val arrowHeadPath = Path().apply {
                            moveTo(size.width * 0.8f, size.height * 0.5f) // Tip of the arrow
                            lineTo(size.width * 0.7f, size.height * 0.4f) // Top of the arrowhead
                            lineTo(size.width * 0.7f, size.height * 0.6f) // Bottom of the arrowhead
                            close()
                        }
                        drawPath(
                            path = arrowHeadPath,
                            color = Color.Black
                        )

                }
        )
    }
    Button(
        onClick = {
            onClearCanvas()
        }
    ) {
        Text(text = "Clear Canvas")
    }
}