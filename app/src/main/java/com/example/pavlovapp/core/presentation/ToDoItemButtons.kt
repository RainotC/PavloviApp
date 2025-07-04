package com.example.pavlovapp.core.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CompleteButton(
    onCompleteCLick: () -> Unit,
    completed : Boolean,
    modifier: Modifier = Modifier
){
    IconButton(onClick={
        onCompleteCLick()
    },
        modifier = modifier)
    {
        if(completed){
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Completed",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.PanoramaFishEye,
                contentDescription = "Uncompleted",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun DeleteButton(
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onDeleteClick,
        modifier = modifier
    )
    {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(32.dp)
        )
    }
}

