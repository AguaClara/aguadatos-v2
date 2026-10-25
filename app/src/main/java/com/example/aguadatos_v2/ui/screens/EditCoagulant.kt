package com.example.aguadatos_v2.ui.screens

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.data.CoagulantRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditCoagulant(
    record: CoagulantRecord,
    onBackClick: () -> Unit,
    onSaveClick: (CoagulantRecord) -> Unit
) {
    val timestamp = SimpleDateFormat(
        "MMM dd, yyyy • hh:mm a",
        Locale.getDefault()
    ).format(Date(record.timestamp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Edit Submission",
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Entry: Coagulant")
            Text("Timestamp: $timestamp")

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Calibration")
            Text("• Slider Position: ${record.sliderPos}")
            Text("• Water Inflow: ${record.waterInflow}")
            Text("• Start Volume: ${record.startVolume}")
            Text("• End Volume: ${record.endVolume}")
            Text("• Time Elapsed: ${record.timeElapsed}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("• Change Dose")
            Text("• Slider Position: ${record.newSliderPos}")
            Text("• Slider Position Over Dose: ${record.sliderPosOverDose}")
            Text("• Chemical Flow Rate: ${record.chemFlowRate}")

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        )
                ) {
                    Text(
                        text = "DELETE",
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF7CBB84),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        )
                ) {
                    Text(
                        text = "SAVE",
                        color = Color.White
                    )
                }
            }
        }
    }
}
